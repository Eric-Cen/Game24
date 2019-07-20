package com.mcarving.game24

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat

class EditTextWithClear : AppCompatEditText{
    private var _clearButtonImage : Drawable?

    constructor(context : Context) : super(context)

    constructor(context: Context, attrs : AttributeSet) : super(context, attrs)

    constructor(context: Context,
                attrs: AttributeSet,
                defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    init {
        _clearButtonImage = ResourcesCompat.getDrawable(resources,
            R.drawable.ic_clear_black_24dp, null)

        addTextChangedListener(object : TextWatcher{

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                showClearButton()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        // if the clear(x) button is tapped, clear the text.
        setOnTouchListener result@ { v, event ->
            if(compoundDrawablesRelative[2] != null){
                var clearButtonStart : Int = 0
                var clearButtonEnd : Int = 0
                var isClearButtonClicked = false

                // Detect the touch in RTL or LTR layout direction
                if(layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                    // if RTL, get the end of the button on the left side
                    _clearButtonImage?.apply{
                        clearButtonEnd = intrinsicWidth + paddingStart
                    }

                    if(event.x < clearButtonEnd){
                        isClearButtonClicked = true
                    }
                } else {
                    // Layout is LTR
                    // Get the start of the button on the right side
                    _clearButtonImage?.apply {
                        clearButtonStart = (width - paddingEnd - intrinsicWidth)
                    }
                    // If the touch occurred after the start of the button,
                    // set isClearButtonClicked to true.
                    if(event.x > clearButtonStart){
                        isClearButtonClicked = true
                    }

                }

                // Check for action if the button is tapped.
                if(isClearButtonClicked){
                    // Check for ACTION_DOWN (always occurs before ACTION_UP).
                    if(event.action == MotionEvent.ACTION_DOWN){
                        // Switch to the black version of clear button.
                        _clearButtonImage = ResourcesCompat.getDrawable(resources,
                            R.drawable.ic_clear_black_24dp, null)
                        showClearButton()
                    }

                    // Check for ACTION_UP
                    if(event.action == MotionEvent.ACTION_UP){
                        // Switch to the opaque version of clear button
                        _clearButtonImage = ResourcesCompat.getDrawable(resources,
                            R.drawable.ic_clear_opaque_24dp,
                            null)
                        // Clear the text and hide the clear button.
                        text?.apply {
                            clear()
                        }
                        hideClearButton()
                        return@result true
                    }
                } else {
                    return@result false
                }

            }

            return@result false
        }
    }

    /**
     * Shows the clear (x) button
     */
    private fun showClearButton(){
        // this method requires a minimum Android API level 17 or newer
        setCompoundDrawablesRelativeWithIntrinsicBounds(
            null, // Start of text
                null, // Above text
                    _clearButtonImage, // End of text
                    null )// Below text
    }

    /**
     * Hides the clear button
     */
    private fun hideClearButton(){
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, // Start of text
                                                        null, // Above text
                                                            null, // End of text
                                                                null ) // Below text
    }
}