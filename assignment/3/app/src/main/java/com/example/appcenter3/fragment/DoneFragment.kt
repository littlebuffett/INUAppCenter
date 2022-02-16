package com.example.appcenter3.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appcenter3.*
import com.example.appcenter3.sharedPreferences.MyApplication
import com.google.android.material.tabs.TabLayout

class DoneFragment : Fragment() {

    private val sharedViewModel:AllList by activityViewModels()

    lateinit var doneadapter: DoneFragment.DoneAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("DoneFragment","객체 생성됨")
        Log.d("DoneFragment ","MyApplication.doneprefs.isFirst()는 ${MyApplication.doneprefs.isFirst()}")
        if(!MyApplication.doneprefs.isFirst()){
            getData()
            Log.d("DoneFragment ","getData()실행됨")
        }

        val rootView = inflater.inflate(R.layout.fragment_done, container, false)
        val doneList:RecyclerView  = rootView.findViewById(R.id.doneList)
        doneList.layoutManager = LinearLayoutManager(rootView.context)

        doneadapter = DoneAdapter(sharedViewModel.DoneItems)
        doneList.adapter = doneadapter

        val viewTab : TabLayout = (activity as MainActivity).findViewById(R.id.viewTab)
        doneList.setOnScrollListener(ScrollListener(viewTab))

        return rootView
    }

    override fun onResume() {
        super.onResume()
        Log.d("IngFragment","onResume()실행됨")
        Log.d("AllList.IngItems의 크기","${sharedViewModel.IngItems.size}")
        doneadapter.notifyDataSetChanged()
        Log.d("IngFragment에서 ","notifyDataSetChanged()실행됨")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("DoneFragment에서 ","onDestroy()실행됨")
        //MyApplication.doneprefs에 데이터 저장.
        setData()
    }

    inner class DoneAdapter(var list: MutableList<ItemData>) : RecyclerView.Adapter<MyViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.list_item,parent,false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

            val imageView = holder.itemView.findViewById<ImageView>(R.id.iv_listImg)
            val Item = list[position]
            holder.bind(Item)
            imageView.setOnClickListener {
                list.removeAt(position)
                Log.d("sharedViewModel.DoneItems의 개수","${sharedViewModel.DoneItems.size}")
                notifyDataSetChanged()
            }
        }

        override fun getItemCount(): Int = list.size
    }

    fun setData(){
        MyApplication.doneprefs.clearAll() //기존의 데이터 모두 삭제.
        for(i in 1..sharedViewModel.DoneItems.size){ //현재 뷰 모델에 있는 데이터로 갱신.
            MyApplication.doneprefs.setText(i,sharedViewModel.DoneItems[i-1].listText)
            MyApplication.doneprefs.setState(i,sharedViewModel.DoneItems[i-1].state)
            MyApplication.doneprefs.setLastItem(i,sharedViewModel.DoneItems[i-1].isLastItme)
            MyApplication.doneprefs.saved(i)
        }
        Log.d("DoneFragment에서 ","setData()실행됨")
        sharedViewModel.DoneItems.clear()
        Log.d("sharedViewModel.DoneItems ","모든 항목 삭제됨")
    }

    fun getData(){
        for (i in 1..MyApplication.doneprefs.getSize()){ //프리퍼런스에 있는 데이터 가져오기.
            var text:String = MyApplication.doneprefs.getText(i)!!
            var state:Int = MyApplication.doneprefs.getState(i)!!
            var isLast:Boolean = MyApplication.doneprefs.getLastItem(i)!!
            sharedViewModel.DoneItems.add(ItemData(text,state,isLast))
            Log.d("DoneFragment에서 ","getData()실행됨")
        }
    }
}