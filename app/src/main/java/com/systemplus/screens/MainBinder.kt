package com.systemplus.screens

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e16din.screensadapter.ScreensAdapter
import com.e16din.screensadapter.annotation.BindScreen
import com.e16din.screensadapter.binders.android.ScreenBinder
import com.e16din.screensadapter.settings.ScreenSettings
import com.systemplus.R
import com.systemplus.app.SystemPlusApp
import com.systemplus.data.TaskData
import com.systemplus.helpers.ItemClickSupport
import com.systemplus.server.RequestManager
import kotlinx.android.synthetic.main.screen_main.view.*

@BindScreen(screen = MainScreen::class)
class MainBinder(adapter: ScreensAdapter<*, *>) : ScreenBinder<MainScreen>(adapter),
        MainScreen.UserAgent,
        MainScreen.SystemAgent {

    companion object {

        fun createScreenSettings() = ScreenSettings(
                screenCls = MainScreen::class.java,
                layoutId = R.layout.screen_main,
                themeId = R.style.AppTheme_NoActionBar
        )
    }

    override fun onBind() {
        screen.userAgent = this
        screen.systemAgent = this
        screen.app = screensAdapter.getApp() as SystemPlusApp
        screen.server = RequestManager

        screen.onBind()
    }

    private val tasksAdapter = TasksAdapter()

    override fun initViews() {
        view.vTasksList.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        view.vTasksList.adapter = tasksAdapter

        ItemClickSupport.addTo(view.vTasksList).addOnItemViewClickListener(R.id.vDoneButton, object : ItemClickSupport.OnItemViewClickListener {
            override fun onItemViewClicked(vList: RecyclerView?, position: Int, v: View?) {
                screen.onDoneClick(position)
            }
        })
    }

    override fun showTasks(tasks: MutableList<TaskData>) {
        tasksAdapter.items = tasks
        tasksAdapter.notifyDataSetChanged()
    }

    override fun showTaskDoneImage(position: Int) {
        tasksAdapter.notifyItemChanged(position)
    }
}