package com.sergio.gymlog.ui.main.exercise.detail

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sergio.gymlog.R
import com.sergio.gymlog.data.model.exercise.Exercises
import com.sergio.gymlog.databinding.DialogExerciseDetailBinding


class ExerciseDetailDialog(
    private val exercise : Exercises
) : BottomSheetDialogFragment() {

    private lateinit var binding : DialogExerciseDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DialogExerciseDetailBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog =  super.onCreateDialog(savedInstanceState)

        dialog.setOnShowListener {
            (it as BottomSheetDialog).let { bottomSheetDialog ->
                (bottomSheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout).let { frameLayout ->
                    BottomSheetBehavior.from(frameLayout).peekHeight = Resources.getSystem().displayMetrics.heightPixels
                    BottomSheetBehavior.from(frameLayout).state = BottomSheetBehavior.STATE_EXPANDED
                }

            }
        }

        return dialog

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind()
    }

    private fun bind() {
        if (exercise.image.isNotBlank()){
            Glide.with(requireContext())
                .load(exercise.image)
                .into(binding.ivExerciseDetailDialogImage)
        }else{
            Glide.with(requireContext())
                .load(R.drawable.logo)
                .into(binding.ivExerciseDetailDialogImage)
        }

        binding.tvExerciseDetailDialogName.text = exercise.name
        binding.tvExerciseDetailDialogDescription.text = exercise.description
        binding.tvExerciseDetailDialogMuscularG.text = getString(exercise.muscularGroup.stringResource)
        binding.tvExerciseDetailDialogMuscularG.setCompoundDrawablesWithIntrinsicBounds(
            null,
            ContextCompat.getDrawable(requireContext(), exercise.muscularGroup.iconResource),
            null,
            null
        )
        binding.tvExerciseDetailDialogEquipment.text = getString(exercise.equipment.stringResource)
        binding.tvExerciseDetailDialogEquipment.setCompoundDrawablesWithIntrinsicBounds(
            null,
            ContextCompat.getDrawable(requireContext(), exercise.equipment.iconResource),
            null,
            null
        )

    }

    override fun onStart() {
        super.onStart()

        val sheetContainer = requireView().parent as ViewGroup
        sheetContainer.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
    }

}