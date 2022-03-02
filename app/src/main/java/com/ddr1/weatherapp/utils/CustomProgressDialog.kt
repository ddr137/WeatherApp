package com.ddr1.weatherapp.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import com.ddr1.weatherapp.R
import com.ddr1.weatherapp.databinding.DialogProgressBinding


class CustomProgressDialog {

    lateinit var dialog: CustomDialog

    fun show(context: Context): Dialog {
        return show(context, null)
    }

    @SuppressLint("InflateParams")
    fun show(context: Context, title: CharSequence?): Dialog {
        val binding: DialogProgressBinding = DialogProgressBinding.inflate(LayoutInflater.from(context))
//        if (title != null) {
//            binding.cpTitle.text = title
//        }

        binding.iconTemp.setAnimation(R.raw.loading_satellite_dish)

        dialog = CustomDialog(context)
        dialog.setContentView(binding.root)
        dialog.show()
        return dialog
    }

    private fun setColorFilter(drawable: Drawable, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
        } else {
            @Suppress("DEPRECATION")
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }

    class CustomDialog(context: Context) : Dialog(context, R.style.CustomDialogTheme) {
        init {
            window?.decorView?.rootView?.setBackgroundResource(R.color.black)
            window?.decorView?.setOnApplyWindowInsetsListener { _, insets ->
                insets.consumeSystemWindowInsets()
            }
        }
    }
}