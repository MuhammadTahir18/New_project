package com.firstproject.new_project.fragements

import android.os.Bundle
import android.provider.CalendarContract.Instances
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import com.firstproject.new_project.Models.dataset
import com.firstproject.new_project.databinding.FragmentAddtaskBinding

import com.google.android.material.textfield.TextInputEditText



class AddTaskFragment : DialogFragment() {
    private lateinit var binding: FragmentAddtaskBinding
    private lateinit var navController: NavController
    private lateinit var listener: DialogNextBtnClickListener
    private  var Dataset: dataset?=null

    fun setlistener(listener:DialogNextBtnClickListener){
        this.listener=listener
    }
    companion object{
        const val Tag="popupFragement"
        @JvmStatic
        fun newInstance(tid:String,task:String)=AddTaskFragment().apply {
            arguments=Bundle().apply {
                putString("tid",tid)
                putString("task",task)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentAddtaskBinding.inflate(inflater,container,false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments!=null){
            Dataset= dataset(
                arguments?.getString("tid").toString(),
                arguments?.getString("task").toString()

            )
            binding.todoEt.setText(Dataset?.task)
        }

        registerEvent()
    }
    private fun registerEvent(){
        binding.todoClose.setOnClickListener{
            dismiss()
        }




        binding.todoNextBtn.setOnClickListener{
            val taskEt= binding.todoEt.text.toString().trim()
            if (taskEt.isNotEmpty()){
                    if (Dataset==null){
                        listener.savetask(taskEt,binding.todoEt)
                    }else{
                        Dataset?.task= taskEt
                        listener.updatetask(Dataset!!,binding.todoEt)
                    }



            }
            else{
                Toast.makeText(context,"please type some tasks",Toast.LENGTH_SHORT).show()
            }
        }}

    interface DialogNextBtnClickListener{
        fun savetask(todo:String, todoEt: TextInputEditText)
        fun updatetask(dataset: dataset, todoEt: TextInputEditText)
    }



}