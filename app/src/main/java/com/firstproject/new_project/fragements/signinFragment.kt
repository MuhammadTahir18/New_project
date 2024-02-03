package com.firstproject.new_project.fragements

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.firstproject.new_project.R
import com.firstproject.new_project.databinding.FragmentSigninBinding
import com.google.firebase.auth.FirebaseAuth


class signinFragment : Fragment() {
private lateinit var binding: FragmentSigninBinding
private lateinit var auth: FirebaseAuth
private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSigninBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        registerEvents()
    }
    private fun init(view: View){
        navController=Navigation.findNavController(view)
        auth=FirebaseAuth.getInstance()
    }
    private fun registerEvents(){
        binding.textViewSignUp.setOnClickListener{
            navController.navigate(R.id.action_signinFragment_to_signupFragment)
        }
        binding.nextBtn.setOnClickListener{
            var email=binding.emailEt.text.toString().trim()
            var pass=binding.passEt.text.toString().trim()

              if (email.isNotEmpty()&& pass.isNotEmpty()){
                  binding.progress.visibility=View.VISIBLE
                     login(email,pass)
              }
            else{
                Toast.makeText(context,"Empty fields are not allowed",Toast.LENGTH_SHORT).show()
              }

        }
    }
    private fun login(email:String,pass:String){
          auth.signInWithEmailAndPassword(email,pass)
              .addOnCompleteListener {
                  task->
                  if(task.isSuccessful){
                      Toast.makeText(context,"login successfull",Toast.LENGTH_SHORT).show()
                      navController.navigate(R.id.action_signinFragment_to_homeFragment)
                  }
                  else{
                      Toast.makeText(context,task.exception.toString(),Toast.LENGTH_SHORT).show()
                  }
              }
        binding.progress.visibility=View.VISIBLE
    }


}