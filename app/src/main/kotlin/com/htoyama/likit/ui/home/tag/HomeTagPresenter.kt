package com.htoyama.likit.ui.home.tag

import com.htoyama.likit.application.tag.TagAppService
import com.htoyama.likit.domain.tag.Tag
import com.htoyama.likit.ui.common.base.BasePresenter
import com.htoyama.likit.ui.home.HomeScope
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

@HomeScope
class HomeTagPresenter @Inject internal constructor(
    private val tagAppService: TagAppService
) : BasePresenter<HomeTagPresenter.View>() {

  interface View {
    fun showProgress()
    fun hideProgress()
    fun goToTagTweetSelectScreen(tag: Tag)
  }

  fun registerNewTag(tagName: String) {
    view?.showProgress()

    subs.add(tagAppService.registerNewTag(tagName)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            { tag ->
              view?.hideProgress()
              view?.goToTagTweetSelectScreen(tag)
            },
            { throwable -> view?.hideProgress() }
        ))
  }

}