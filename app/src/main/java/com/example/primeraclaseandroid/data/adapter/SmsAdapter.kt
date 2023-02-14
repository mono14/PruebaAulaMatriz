package com.example.primeraclaseandroid.data.adapter

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import com.example.primeraclaseandroid.R
import com.example.primeraclaseandroid.databinding.ItemSmsBinding
import java.util.*

class SmsAdapter(
    context: Context,
    c: Cursor,
    autoRequery: Boolean
) : CursorAdapter(context, c, autoRequery) {

    private lateinit var binding: ItemSmsBinding

    //infla el xml
    override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(
            R.layout.item_sms,//coge el xml de los items
            parent,
            false
        )
        binding = ItemSmsBinding.bind(itemView)
        return binding.root
    }

    //enlaza los datos con la vista
    override fun bindView(view: View, context: Context, cursor: Cursor) {
        binding.smsOrigin.text = cursor.getString(cursor.getColumnIndexOrThrow(SmsColumns.ADDRESS))
        binding.smsBody.text =  cursor.getString(cursor.getColumnIndexOrThrow(SmsColumns.BODY))
        binding.smsDate.text = Date(
            cursor.getLong(
                cursor.getColumnIndexOrThrow(SmsColumns.DATE)
            )
        ).toString()
    }

}

object SmsColumns {
    val ID = "_id"
    val ADDRESS = "address"
    val DATE = "date"
    val BODY = "body"
}
