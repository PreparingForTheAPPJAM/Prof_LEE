package org.techtown.prof_lee

import android.os.Bundle
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import org.techtown.prof_lee.databinding.ActivityReadBinding
import kotlin.properties.Delegates

class ReadActivity : BaseActivity<ActivityReadBinding>(R.layout.activity_read) {
    private lateinit var readViewPagerAdapter: ReadViewPagerAdapter
    private val readViewModel by viewModels<ReadViewModel>()
    private var readId by Delegates.notNull<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.readViewModel = readViewModel

        initReadId()
        initReadItem()
        initReadItemObserver()
        initViewPagerAdapter()
        initBackBtnClickListener()
        initStateLayoutClickListener()
        initHeartBtnClickListener()
    }

    private fun initReadId() {
        readId = intent.getStringExtra("id").toString()
    }

    private fun initReadItem() {
        readViewModel.getReadItem(readId)
    }

    private fun initReadItemObserver() {
        readViewModel.readItem.observe(this) {
            readViewPagerAdapter.updateHabitList(it.image)
            binding.readViewModel = readViewModel
        }

        readViewModel.isLiked.observe(this) {
            binding.readViewModel = readViewModel
        }

        readViewModel.state.observe(this) {
            binding.readViewModel = readViewModel
        }
    }

    private fun initViewPagerAdapter() {
        readViewPagerAdapter = ReadViewPagerAdapter()
        binding.vpReadImage.adapter = readViewPagerAdapter
        binding.vpReadImage.orientation = ViewPager2.ORIENTATION_HORIZONTAL


    }

    private fun initBackBtnClickListener() {
        binding.btnReadBack.setOnClickListener {
            finish()
        }
    }

    private fun initStateLayoutClickListener() {
        binding.layoutReadState.setOnClickListener {
            StateBottomSheet().show(supportFragmentManager, this.javaClass.name)
        }
    }

    private fun initHeartBtnClickListener() {
        binding.btnReadHeart.setOnClickListener {
            readViewModel.initHeart()
        }
    }
}