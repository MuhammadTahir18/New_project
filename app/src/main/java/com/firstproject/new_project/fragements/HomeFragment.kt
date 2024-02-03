package com.firstproject.new_project.fragements

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.service.autofill.Dataset
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firstproject.new_project.Adapters.sAdapter
import com.firstproject.new_project.Models.dataset
import com.firstproject.new_project.R
import com.firstproject.new_project.databinding.FragmentHomeBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeFragment : Fragment(), AddTaskFragment.DialogNextBtnClickListener,
    sAdapter.AdapterClickInterface {
   private lateinit var binding:FragmentHomeBinding
   private lateinit var databaseRef: DatabaseReference
   private lateinit var navController: NavController
   private lateinit var auth: FirebaseAuth
   private  var popupfrag: AddTaskFragment?=null
         private lateinit var recyclerView: RecyclerView
        private lateinit var adapter:sAdapter
        private lateinit var mlist:MutableList<dataset>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        getdataFormFirbase()
        registerEvent()





    }
    private fun init(view: View){
        navController=Navigation.findNavController(view)
        auth=FirebaseAuth.getInstance()
        databaseRef=FirebaseDatabase.getInstance().reference
            .child("Tasks").child(auth.currentUser?.uid.toString())
                 /////////// feth data ////////
            binding.recycler.setHasFixedSize(true)
        binding.recycler.layoutManager=LinearLayoutManager(context)
        mlist= mutableListOf()
          adapter= sAdapter(mlist)

           adapter.setlistener(this)
        binding.recycler.adapter=adapter


    }
    private fun registerEvent(){
        binding.addTaskBtn.setOnClickListener{

            if (popupfrag!=null){
                childFragmentManager.beginTransaction().remove(popupfrag!!).commit()
            }
            popupfrag= AddTaskFragment()
            popupfrag!!.setlistener(this)
            popupfrag!!.show(
                childFragmentManager,
                AddTaskFragment.Tag
            )
        }

    }
    private fun getdataFormFirbase(){
        databaseRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                mlist.clear()
                for (tasksnap in snapshot.children){
                    val child=tasksnap.key?.let {
                        dataset(it,tasksnap.value.toString())
                    }
                    if (child!=null){
                        mlist.add(child)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,error.message,Toast.LENGTH_SHORT).show()
            }

        })
    }


    override fun savetask(todo: String, todoEt: TextInputEditText) {
            databaseRef.push().setValue(todo).addOnCompleteListener {
                task->
                if (task.isSuccessful){
                    Toast.makeText(context,"Task added successfull",Toast.LENGTH_SHORT).show()

                }
                else{
                    Toast.makeText(context,"Task not added",Toast.LENGTH_SHORT).show()
                }
                todoEt.text=null
                popupfrag!!.dismiss()
            }

    }

    override fun updatetask(dataset: dataset, todoEt: TextInputEditText) {
          val map=HashMap<String,Any>()
          map[dataset.tid]=dataset.task
        databaseRef.updateChildren(map)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(context,"update successfull ",Toast.LENGTH_SHORT).show()

                }else{
                    Toast.makeText(context,"Task not  Edit successful ",Toast.LENGTH_SHORT).show()
                }
            }
        todoEt.text=null
        popupfrag!!.dismiss()
    }

    override fun ondeleteBtnClick(data: dataset) {
       databaseRef.child(data.tid).removeValue().addOnCompleteListener {
           if (it.isSuccessful){
               val dialog=AlertDialog.Builder(context)
               dialog.setTitle("Alert!!!!")
               dialog.setMessage("Do you want to delete task?")
               dialog.setPositiveButton("yes"){
                       dialog,which->
               }
               dialog.setNegativeButton("No"){
                       dialog,which->
                   dialog.dismiss()
               }
               dialog.setCancelable(false)
               dialog.show()
               Toast.makeText(context,"Task  successfully   removed ",Toast.LENGTH_SHORT).show()
           }else{
               Toast.makeText(context,"Task  Not remove ",Toast.LENGTH_SHORT).show()
           }



       }
    }

    override fun oneditBtnClick(data: dataset) {
           if (popupfrag!=null){
               childFragmentManager.beginTransaction().remove(popupfrag!!).commit()
           }
        popupfrag=AddTaskFragment.newInstance(data.tid,data.task)
        popupfrag!!.setlistener(this)
        popupfrag!!.show(childFragmentManager,AddTaskFragment.Tag)
    }


}





