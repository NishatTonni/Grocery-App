package com.tonni.groceryapp

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), GroceryRVAdapter.GroceryItemClickInterface {

    lateinit var itemRV: RecyclerView
    lateinit var addFAB: FloatingActionButton
    lateinit var list: List<GroceryItems>
    lateinit var groceryRVAdapter: GroceryRVAdapter
    lateinit var groceryViewModel: GroceryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemRV = findViewById(R.id.RVItemsId)
        addFAB = findViewById(R.id.FABAddId)
        list = ArrayList<GroceryItems>()
        groceryRVAdapter = GroceryRVAdapter(list,this)
        itemRV.layoutManager = LinearLayoutManager(this)
        itemRV.adapter = groceryRVAdapter

        val groceryRepository = GroceryRepository(GroceryDatabase(this))
        val factory = GroceryViewModelFactory(groceryRepository)
        groceryViewModel = ViewModelProvider(this,factory).get(GroceryViewModel::class.java)
        groceryViewModel.getAllGroceryItems().observe(this, Observer {
            groceryRVAdapter.list = it
            groceryRVAdapter.notifyDataSetChanged()
        })

        addFAB.setOnClickListener {
           openDialog()
        }
        

    }
    fun openDialog(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.grocery_add_dialog)

        val cancelBtn = dialog.findViewById<Button>(R.id.cancelBtnId)
        val addBtn = dialog.findViewById<Button>(R.id.addBtnId)
        val itemNameEdt= dialog.findViewById<EditText>(R.id.EdtItemNameId)
        val itemPriceEdt= dialog.findViewById<EditText>(R.id.EdtItemPriceId)
        val itemQuantityEdt= dialog.findViewById<EditText>(R.id.EdtItemQuantityId)

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        addBtn.setOnClickListener {
            val itemNameEdt : String = itemNameEdt.text.toString()
            val itemPriceEdt : String = itemPriceEdt.text.toString()
            val itemQuantityEdt : String = itemQuantityEdt.text.toString()
            val quantity : Int = itemQuantityEdt.toInt()
            val price : Int = itemPriceEdt.toInt()

            if(itemNameEdt.isNotEmpty() && itemPriceEdt.isNotEmpty() && itemQuantityEdt.isNotEmpty()){
                val items = GroceryItems(itemNameEdt,quantity,price)
                groceryViewModel.insert(items)
                Toast.makeText(applicationContext,"Item Inserted..",Toast.LENGTH_SHORT).show()
                groceryRVAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }else{
                Toast.makeText(applicationContext,"Please enter all the data..",Toast.LENGTH_SHORT).show()

            }
        }

        dialog.show()

    }

    override fun onItemClick(groceryItems: GroceryItems) {
        groceryViewModel.delete(groceryItems)
        groceryRVAdapter.notifyDataSetChanged()
        Toast.makeText(applicationContext,"Item Deleted",Toast.LENGTH_SHORT).show()

    }
}