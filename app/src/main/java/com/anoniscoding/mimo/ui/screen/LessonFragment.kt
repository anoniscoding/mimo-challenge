package com.anoniscoding.mimo.ui.screen

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.anoniscoding.mimo.R
import com.anoniscoding.mimo.databinding.FragmentLessonBinding
import com.anoniscoding.mimo.ui.presentation.intent.LessonIntent
import com.anoniscoding.mimo.ui.presentation.intent.LessonViewEffect
import com.anoniscoding.mimo.ui.presentation.intent.LessonViewState
import com.anoniscoding.mimo.ui.presentation.state.DataState
import com.anoniscoding.mimo.ui.presentation.viewmodel.LessonViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LessonFragment : Fragment() {
    private val viewModel: LessonViewModel by viewModels()
    private var _binding: FragmentLessonBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLessonBinding.inflate(inflater, container, false)
        _binding?.lifecycleOwner = viewLifecycleOwner
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerObservers()
        registerListeners()
    }

    private fun registerObservers() {
        viewModel.viewEffects.observe(viewLifecycleOwner, { onViewEffectReceived(it) })
        viewModel.dataStates.observe(viewLifecycleOwner, { onDataStateReceived(it) })
    }

    private fun registerListeners() {
        binding.inputText.doAfterTextChanged {
            viewModel.setIntent(LessonIntent.OnInputEvent(it.toString()))
        }
        binding.nextButton.setOnClickListener { viewModel.setIntent(LessonIntent.OnNextEvent) }
    }

    private fun onViewEffectReceived(it: LessonViewEffect?) {
        when (it) {
            LessonViewEffect.NavigateToDoneScreen -> navigateToDoneScreen()
        }
    }

    private fun onDataStateReceived(dataState: DataState<LessonViewState>?) {
        dataState?.toData()?.let { displayCurrentLesson(it) }
        dataState?.toErrorMessage()?.let { displayError(it) }
        binding.loader.isVisible = dataState?.isLoading() ?: false
    }

    private fun displayCurrentLesson(viewState: LessonViewState) {
        binding.nextButton.isVisible = true
        binding.nextButton.isEnabled = viewState.isNextButtonEnabled
        binding.finalText.isVisible = viewState.currentLesson.hasInput()
        binding.inputText.isVisible = viewState.currentLesson.hasInput()

        val fullContentAsHtmlSpanned = viewState.currentLesson.getFullContentAsHtmlSpanned()
        if (viewState.currentLesson.hasInput()) {
            val startIndex = viewState.currentLesson.input?.startIndex ?: 0
            val endIndex = viewState.currentLesson.input?.endIndex ?: 0
            binding.initialText.text = fullContentAsHtmlSpanned.subSequence(0, startIndex)
            binding.finalText.text = fullContentAsHtmlSpanned
                .subSequence(endIndex, fullContentAsHtmlSpanned.length)
            binding.inputText.setTextColor(Color.parseColor(viewState.currentLesson.getAnswerColor()))
        } else {
            binding.initialText.text = fullContentAsHtmlSpanned
        }
    }

    private fun displayError(message: String) {
        Snackbar.make(
            requireActivity().findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_LONG
        ).setAction(getString(R.string.snack_label_lesson_screen_retry)) {
            viewModel.setIntent(LessonIntent.OnRetryEvent)
        }.show()
    }

    //Prevent memory leak by de-referencing binding
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToDoneScreen() {
        findNavController().navigate(LessonFragmentDirections.actionLessonFragmentToDoneFragment())
    }
}