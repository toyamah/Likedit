package com.htoyama.licol.ui.home.user.list

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.htoyama.licol.R
import com.htoyama.licol.domain.user.User
import com.squareup.picasso.Picasso
import javax.inject.Inject

/**
 * Created by htoyama on 2017/05/14.
 */
class UserController @Inject constructor() : EpoxyController() {
  private var userList: List<User> = emptyList()
  private var requireLoadingMore: Boolean = true

  fun setData(l: List<User>, requireLoadingMore: Boolean) {
    userList = l
    this.requireLoadingMore = requireLoadingMore
    requestModelBuild()
  }

  override fun buildModels() {
    userList.forEach {
      UserModel(it)
          .id(it.id)
          .addTo(this)
    }
  }
}

class UserModel(
    private val user: User
) : EpoxyModelWithHolder<UserModel.Holder>() {

  override fun createNewHolder(): Holder = Holder()

  override fun getDefaultLayout(): Int = R.layout.home_user_list_item

  override fun bind(h: Holder) {
    //TODO: use Glide
    Picasso.with(h.context)
        .load(user.avatorUrl)
        .into(h.avatarView)

    h.nameView.text = user.name
    h.screenNameView.text = user.screenName
  }

  class Holder : EpoxyHolder() {
    lateinit var context: Context
    lateinit var avatarView: ImageView
    lateinit var nameView: TextView
    lateinit var screenNameView: TextView

    override fun bindView(itemView: View) {
      context = itemView.context
      //TODO: use ButterKnife or something like it
      avatarView = itemView.findViewById(R.id.user_avatar) as ImageView
      nameView = itemView.findViewById(R.id.user_name) as TextView
      screenNameView = itemView.findViewById(R.id.user_screen_name) as TextView
    }
  }

}