package com.example.appcenter3.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appcenter3.*
import com.example.appcenter3.sharedPreferences.MyApplication
import com.google.android.material.tabs.TabLayout

class BeforeFragment : androidx.fragment.app.Fragment() {

    private val sharedViewModel:AllList by activityViewModels()

    lateinit var beforeadapter:BeforeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("BeforeFragment","객체 생성됨")
        //MyApplication.beforeprefs에서 데이터 가져오기.
        Log.d("BeforeFragment","MyApplication.beforeprefs.isFirst()는 ${MyApplication.beforeprefs.isFirst()}")
        if(!MyApplication.beforeprefs.isFirst()){
            getData()
            Log.d("BeforeFragment에서 ","getData()실행됨")
        }


        val rootView = inflater.inflate(R.layout.fragment_before, container, false)
        val beforeList:RecyclerView = rootView.findViewById(R.id.beforeList)
        beforeList.layoutManager = LinearLayoutManager(rootView.context)
        sharedViewModel.BeforeItems.add(ItemData("추가 버튼",0,true))
        beforeadapter = BeforeAdapter(sharedViewModel.BeforeItems)
        beforeList.adapter = beforeadapter


        //스크롤 내릴 때 탭 레이아웃 그림자 주기.
        val viewTab : TabLayout = (activity as MainActivity).findViewById(R.id.viewTab)
        beforeList.setOnScrollListener(ScrollListener(viewTab))

        return rootView
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("BeforeFragment에서 ","onDestroy()실행됨")
        //MyApplication.beforeprefs에 데이터 저장.
        setData()
    }

    inner class BeforeAdapter(val list: MutableList<ItemData>) : RecyclerView.Adapter<MyViewHolder>(){

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
                if(Item.isLastItme){ //사용자로부터 텍스트를 입력받아 새로운 할 일 추가.
                    var newText:String=""
                    val newItemDialog = NewItemDialog(R.layout.dialog,list,Item,beforeadapter)
                    fragmentManager?.let { newItemDialog.show(it, "CustomDialog") }
                    //val newItemData = ItemData(newText,0,false)
                    //list.add(list.indexOf(Item),newItemData)
                }
                else{
                    list.removeAt(position)
                    sharedViewModel.addToIng(Item)
                    Log.d("시작 전 ","아이템 클릭")
                    Log.d("allList.IngItems에","state1로 변경된 아이템 추가됨")
                }
                notifyDataSetChanged()
            }
        }
        override fun getItemCount(): Int = list.size
    }


    fun setData(){
        MyApplication.beforeprefs.clearAll() //기존의 데이터 모두 삭제.
        for(i in 1..sharedViewModel.BeforeItems.size-1){ //현재 뷰 모델에 있는 데이터로 갱신.
            MyApplication.beforeprefs.setText(i,sharedViewModel.BeforeItems[i-1].listText)
            MyApplication.beforeprefs.setState(i,sharedViewModel.BeforeItems[i-1].state)
            MyApplication.beforeprefs.setLastItem(i,sharedViewModel.BeforeItems[i-1].isLastItme)
            MyApplication.beforeprefs.saved(i)
        }
        Log.d("BeforeFragment에서 ","setData()실행됨")
        sharedViewModel.BeforeItems.clear()
        Log.d("sharedViewModel.BeforeItems ","모든 항목 삭제됨")
    }

    fun getData(){
        for (i in 1..MyApplication.beforeprefs.getSize()){ //프리퍼런스에 있는 데이터 가져오기.
            var text:String = MyApplication.beforeprefs.getText(i)!!
            var state:Int = MyApplication.beforeprefs.getState(i)!!
            var isLast:Boolean = MyApplication.beforeprefs.getLastItem(i)!!
            sharedViewModel.BeforeItems.add(ItemData(text,state,isLast))
            Log.d("BeforeFragment에서 ","getData()실행됨")
        }
    }

}