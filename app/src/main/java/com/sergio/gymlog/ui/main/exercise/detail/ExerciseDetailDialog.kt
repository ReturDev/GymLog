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
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sergio.gymlog.R
import com.sergio.gymlog.data.model.exercise.Exercises
import com.sergio.gymlog.databinding.DialogExerciseDetailBinding
import com.sergio.gymlog.util.extension.setImageRoundedBorders


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind()
    }

    private fun bind() {

        val alpha = if (exercise.image.isBlank()){
            0.75f
        }else{
            1f
        }

        val image = exercise.image.ifBlank {
            R.drawable.logo
        }

        Glide.with(binding.root.context)
            .load(image)
            .transform(
                MultiTransformation(
                    CenterCrop(),
                    RoundedCorners(20)
                )
            )
            .into(binding.ivExerciseDetailDialogImage)
        binding.ivExerciseDetailDialogImage.alpha = alpha


        binding.tvExerciseDetailDialogName.text = exercise.name


       if (exercise.description.isNotBlank()){

           binding.tvExerciseDetailDialogDescription.text = exercise.description
           binding.tvExerciseDetailDialogDescription.visibility = View.VISIBLE

       }else{

           binding.tvExerciseDetailDialogDescription.visibility = View.GONE

       }


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


}