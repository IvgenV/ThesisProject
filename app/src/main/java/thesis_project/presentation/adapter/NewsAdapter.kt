package thesis_project.presentation.adapter

import android.animation.LayoutTransition
import android.content.Intent
import android.content.SharedPreferences
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.thesis_project.R
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso
import thesis_project.data.data_base.news.News

class NewsAdapter: ListAdapter<News,
        NewsAdapter.ViewHolder>(NewsCompareCallback()) {

    private var listener: ToFragmentNews? = null
    private val MAX_LINES_COLLAPSED = 3
    private val INITIAL_IS_COLLAPSED = true
    private val IDLE_ANIMATION_STATE = 1
    private val EXPANDING_ANIMATION_STATE = 2
    private val COLLAPSING_ANIMATION_STATE = 3
    private var mCurrentAnimationState = IDLE_ANIMATION_STATE
    private var isCollapsed = INITIAL_IS_COLLAPSED

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_item_for_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textTitle.text = getItem(position).name_ru + "skdsdkskdskd\nsdsdsdsdsd\nsdsdsds"
        holder.start_data.text = getItem(position).start_date

        Picasso.get().load(getItem(position).img).into(holder.image)



        holder.expand.setOnClickListener {
            if(isRunning()){
                holder.titleLayout.layoutTransition = holder.titleLayout.layoutTransition
            }
            if (isCollapsed) {
                mCurrentAnimationState = EXPANDING_ANIMATION_STATE
                holder.expand.setImageResource(R.drawable.ic_expand_less)
                holder.textTitle.maxLines = Int.MAX_VALUE
            } else {
                mCurrentAnimationState = COLLAPSING_ANIMATION_STATE
                holder.textTitle.maxLines = MAX_LINES_COLLAPSED;
                holder.textTitle.post {
                    holder.textTitle.maxLines = Int.MAX_VALUE
                }
                holder.expand.setImageResource(R.drawable.ic_expand_more)
            }
            isCollapsed = !isCollapsed;
        }

        if(isCollapsed){
            holder.textTitle.maxLines = MAX_LINES_COLLAPSED
        }else{
            holder.textTitle.maxLines = Int.MAX_VALUE
        }

        updateWithNewText(holder)
        applyLayoutTransition(holder)

        holder.wholeNews.setOnClickListener {
            listener?.onClick(getItem(position))
        }
        holder.share.setOnClickListener {
            listener?.share(holder.textTitle.text.toString())
        }
        listener?.SharedPreferences(holder.card,holder.textTitle.text.toString())
    }

    private fun updateWithNewText(holder:ViewHolder) {
        holder.textTitle.viewTreeObserver
            .addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    if (isTextUnlimited(holder)) {
                        if (canBeCollapsed(holder)) {
                            holder.textTitle.ellipsize = null
                        } else {
                            holder.textTitle.ellipsize = TextUtils.TruncateAt.END
                        }
                    } else {
                        if (isTrimmedWithLimitLines(holder)) {
                            holder.expand.visibility = View.INVISIBLE
                            holder.textTitle.ellipsize = null
                        } else {
                            holder.expand.visibility = View.VISIBLE
                            holder.textTitle.ellipsize = TextUtils.TruncateAt.END
                        }
                    }
                    holder.textTitle.viewTreeObserver
                        .removeOnGlobalLayoutListener(this)
                }
            })
    }

    private fun isTextUnlimited(holder:ViewHolder): Boolean {
        return holder.textTitle.maxLines == Int.MAX_VALUE
    }

    private fun canBeCollapsed(holder:ViewHolder): Boolean {
        return holder.textTitle.lineCount <= MAX_LINES_COLLAPSED
    }

    private fun isTrimmedWithLimitLines(holder:ViewHolder): Boolean {
        return holder.textTitle.lineCount <= holder.textTitle.maxLines
    }

    private fun applyLayoutTransition(holder:ViewHolder){
        val transition = LayoutTransition()
        transition.setDuration(300)
        transition.enableTransitionType(LayoutTransition.CHANGING)
        holder.titleLayout.layoutTransition = transition

        transition.addTransitionListener(object : LayoutTransition.TransitionListener{
            override fun startTransition(
                transition: LayoutTransition?,
                container: ViewGroup?,
                view: View?,
                transitionType: Int
            ) {

            }

            override fun endTransition(
                transition: LayoutTransition?,
                container: ViewGroup?,
                view: View?,
                transitionType: Int
            ) {
                if (COLLAPSING_ANIMATION_STATE == mCurrentAnimationState) {
                    holder.textTitle.maxLines = MAX_LINES_COLLAPSED;
                }
                mCurrentAnimationState = IDLE_ANIMATION_STATE;
            }

        })
    }

    private fun isIdle(): Boolean {
        return mCurrentAnimationState == IDLE_ANIMATION_STATE
    }

    private fun isRunning(): Boolean {
        return !isIdle()
    }

    fun setListener(toFragmentNews: ToFragmentNews) {
        listener = toFragmentNews
    }

    fun setData(data: List<News>) {
        submitList(data)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val start_data: TextView = view.findViewById(R.id.textDate)
        val wholeNews:TextView = view.findViewById(R.id.textWholeNews)
        val expand: ImageView = view.findViewById(R.id.imageExpand)
        val card: MaterialCardView = view.findViewById(R.id.card)
        val share:ImageView = view.findViewById(R.id.imageShare)
        val textTitle: TextView = view.findViewById(R.id.text_title)
        val titleLayout: ConstraintLayout = view.findViewById(R.id.root_container)
        val image:ImageView = view.findViewById(R.id.image)
    }
}