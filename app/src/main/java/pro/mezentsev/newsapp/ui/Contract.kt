package pro.mezentsev.newsapp.ui

import androidx.annotation.CallSuper

interface Contract {
    interface BaseView

    abstract class BasePresenter<V: BaseView> {
        protected var view: V? = null

        @CallSuper
        open fun attach(v: V) {
            view = v
        }

        @CallSuper
        open fun detach() {
            view = null
        }
    }
}