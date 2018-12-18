package com.dcmobile.messenger7egend

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import com.dcmobile.messenger7egend.db.MyDatabase
import com.dcmobile.messenger7egend.network.HostService
import com.dcmobile.messenger7egend.repo.MessageRepository
import com.dcmobile.messenger7egend.viewModel.FactoryViewModel
import com.dcmobile.messenger7egend.viewModel.MessageDataViewModel
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: MessageDataViewModel
    private val adapter = Adapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val service = HostService.create()
        val dao = Room.databaseBuilder(application, MyDatabase::class.java, "my-messenger-db").build()
        val factory = FactoryViewModel(MessageRepository(service, dao.messageDao(), Executors.newSingleThreadExecutor()))
        viewModel = ViewModelProviders.of(this, factory).get(MessageDataViewModel::class.java)

        initViews()

        viewModel.getMessages()?.observe(this, Observer { messages ->
            if (messages != null) {
                adapter.addData(messages)
            }
        })
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.adapter = adapter
    }

}
