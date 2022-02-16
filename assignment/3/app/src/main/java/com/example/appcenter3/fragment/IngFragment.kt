package com.example.appcenter3.fragment

import android.content.Context
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

class IngFragment : Fragment() {

    private val sharedViewModel:AllList by activityViewModels()

    lateinit var ingadapter:IngAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("IngFragment","객체 생성됨")

        //MyApplication.ingprefs에서 데이터 가져오기.
        Log.d("IngFragment ","MyApplication.ingprefs.isFirst()는 ${MyApplication.ingprefs.isFirst()}")
        if(!MyApplication.ingprefs.isFirst()){
            getData()
            Log.d("IngFragment ","getData()실행됨")
        }

        val rootView = inflater.inflate(R.layout.fragment_ing, container, false)
        val ingList:RecyclerView = rootView.findViewById(R.id.ingList)
        ingList.layoutManager = LinearLayoutManager(rootView.context)

        Log.d("AllList.IngItems의 크기","${sharedViewModel.IngItems.size}")
        ingadapter = IngAdapter(sharedViewModel.IngItems)
        ingList.adapter = ingadapter

        val viewTab : TabLayout = (activity as MainActivity).findViewById(R.id.viewTab)
        ingList.setOnScrollListener(ScrollListener(viewTab))
        return rootView
    }

    override fun onResume() {
        super.onResume()
        Log.d("IngFragment","onResume()실행됨")
        Log.d("AllList.IngItems의 크기","${sharedViewModel.IngItems.size}")
        ingadapter.notifyDataSetChanged()
        Log.d("IngFragment에서 ","notifyDataSetChanged()실행됨")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("IngFragment에서 ","onDestroy()실행됨")
        //MyApplication.ingprefs에 데이터 저장.
        setData()
    }

    inner class IngAdapter(var list: MutableList<ItemData>) : RecyclerView.Adapter<MyViewHolder>(){

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
                sharedViewModel.addToDone(Item)
                notifyDataSetChanged()
            }
        }
        override fun getItemCount(): Int = list.size
    }


    fun setData(){
        MyApplication.ingprefs.clearAll() //기존의 데이터 모두 삭제.
        for(i in 1..sharedViewModel.IngItems.size){ //현재 뷰 모델에 있는 데이터로 갱신.
            MyApplication.ingprefs.setText(i,sharedViewModel.IngItems[i-1].listText)
            MyApplication.ingprefs.setState(i,sharedViewModel.IngItems[i-1].state)
            MyApplication.ingprefs.setLastItem(i,sharedViewModel.IngItems[i-1].isLastItme)
            MyApplication.ingprefs.saved(i)
        }
        Log.d("BeforeFragment에서 ","setData()실행됨")
        sharedViewModel.IngItems.clear()
        Log.d("sharedViewModel.IngItems ","모든 항목 삭제됨")
    }

    fun getData(){
        for (i in 1..MyApplication.ingprefs.getSize()){ //프리퍼런스에 있는 데이터 가져오기.
            var text:String = MyApplication.ingprefs.getText(i)!!
            var state:Int = MyApplication.ingprefs.getState(i)!!
            var isLast:Boolean = MyApplication.ingprefs.getLastItem(i)!!
            sharedViewModel.IngItems.add(ItemData(text,state,isLast))
            Log.d("BeforeFragment에서 ","getData()실행됨")
        }
    }

}