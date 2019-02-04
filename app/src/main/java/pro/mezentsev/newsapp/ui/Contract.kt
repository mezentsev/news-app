package pro.mezentsev.newsapp.ui

import androidx.annotation.CallSuper

interface Contract {
    interface BaseView

    abstract class BasePresenter<V: BaseView> {
        protected var view: V? = null

        /**
         * Attaches view to presenter.
         */
        @CallSuper
        open fun attach(v: V) {
            view = v
        }

        /**
         * Detaches view from presenter.
         */
        @CallSuper
        open fun detach() {
            view = null
        }
    }
}