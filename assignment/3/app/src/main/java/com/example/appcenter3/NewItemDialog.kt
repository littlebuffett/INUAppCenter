package com.example.appcenter3

import android.content.ClipData
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.appcenter3.fragment.BeforeFragment

class NewItemDialog(private val layoutResId: Int, val list: MutableList<ItemData>,val Item:ItemData, val adapter:BeforeFragment.BeforeAdapter): DialogFragment() {

    lateinit var dialogView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialogView = inflater.inflate(layoutResId, container, false)
        val editText:EditText = dialogView.findViewById(R.id.et_dialoginput)
        val btn_back:ImageButton = dialogView.findViewById(R.id.btn_back)
        btn_back.setOnClickListener {
            dismiss()
        }
        val btn_okay:ImageButton = dialogView.findViewById(R.id.btn_okay)
        btn_okay.setOnClickListener {
            var text:String = editText.text.toString()
            val newItemData = ItemData(text,0,false)
            list.add(list.indexOf(Item),newItemData)
            adapter.notifyDataSetChanged()
            dismiss()
        }

        return dialogView
    }

    override fun onResume() {
        super.onResume()
        // full Screen 으로 dialog세팅.
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }

}