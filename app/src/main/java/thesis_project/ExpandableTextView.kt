package thesis_project

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.widget.AppCompatTextView

class ExpandableTextView : AppCompatTextView, View.OnClickListener {
    private val COLLAPSED_MAX_LINES = 3
    lateinit var mAnimator: ValueAnimator
    private var isCollapsing = false
    private var mOriginalText: CharSequence? = null
    private val POSTFIX = "...see more "


    constructor(context: Context?) : super(context!!) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!, attrs, defStyleAttr
    ) {
        init()
    }

    private fun init() {
        maxLines = COLLAPSED_MAX_LINES
        setOnClickListener(this)
        initAnimator()
    }

    private fun initAnimator() {
        mAnimator = ValueAnimator.ofInt(-1, -1)
            .setDuration(450)
        mAnimator.interpolator = AccelerateDecelerateInterpolator()
        mAnimator.addUpdateListener { valueAnimator ->
            updateHeight(
                valueAnimator.animatedValue as Int
            )
        }
        mAnimator.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationStart(animation: Animator) {
                if (isCollapsed()) {
                    isCollapsing = false
                    maxLines = Int.MAX_VALUE
                    deEllipsize()
                } else {
                    isCollapsing = true
                }
            }

            override fun onAnimationEnd(animation: Animator) {
                if (!isCollapsed() && isCollapsing) {
                    maxLines = COLLAPSED_MAX_LINES
                    ellipsizeColored()
                    isCollapsing = false
                }
                setWrapContent()
            }
        })
    }

    override fun setText(text: CharSequence, type: BufferType) {
        mOriginalText = text
        super.setText(text, type)
    }

    private fun ellipsizeColored() {
        val end = layout.getLineEnd(COLLAPSED_MAX_LINES - 1)
        val text = text
        val chars = (layout.getLineEnd(COLLAPSED_MAX_LINES - 1)
                - layout.getLineStart(COLLAPSED_MAX_LINES - 1))
        val additionalGap = 4
        if (chars + additionalGap < POSTFIX.length) {
            return
        }
        val builder = SpannableStringBuilder(text)
        builder.replace(end - POSTFIX.length, end, POSTFIX)
        builder.setSpan(
            ForegroundColorSpan(Color.BLACK),
            end - POSTFIX.length, end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        setTextNoCaching(builder)
    }

    private fun deEllipsize() {
        super.setText(mOriginalText)
    }

    private fun setTextNoCaching(text: CharSequence?) {
        super.setText(text, BufferType.NORMAL)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (lineCount <= COLLAPSED_MAX_LINES) {
            deEllipsize()
            isClickable = false
        } else {
            isClickable = true
            if (!mAnimator.isRunning && isCollapsed()) {
                ellipsizeColored()
            }
        }
    }

    override fun onClick(v: View) {
        if (mAnimator.isRunning) {
            animatorReverse()
            return
        }
        val endPosition = animateTo()
        val startPosition = height
        mAnimator.setIntValues(startPosition, endPosition)
        animatorStart()
    }

    private fun animatorReverse() {
        isCollapsing = !isCollapsing
        mAnimator.reverse()
    }

    private fun animatorStart() {
        mAnimator.start()
    }

    private fun animateTo(): Int {
        return if (isCollapsed()) {
            layout.height + paddingHeight()
        } else {
            (layout.getLineBottom(COLLAPSED_MAX_LINES - 1)
                    + layout.bottomPadding + paddingHeight())
        }
    }

    private fun paddingHeight(): Int {
        return compoundPaddingBottom + compoundPaddingTop
    }

    private fun isCollapsed(): Boolean {
        return Int.MAX_VALUE != maxLines;
    }

    private fun updateHeight(animatedValue: Int) {
        val layoutParams = layoutParams
        layoutParams.height = animatedValue
        setLayoutParams(layoutParams)
    }

    private fun setWrapContent() {
        val layoutParams = layoutParams
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        setLayoutParams(layoutParams)
    }
}