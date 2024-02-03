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
import com.firstproject.new_project.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth

class signupFragment : Fragment() {
   private lateinit var binding:FragmentSignupBinding
   private lateinit var navController: NavController
   private lateinit var auth:FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSignupBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        registerEvent()
    }
    private fun init(view: View){
        navController=Navigation.findNavController(view)
        auth=FirebaseAuth.getInstance()
    }
    private fun registerEvent(){
        binding.textViewSignIn.setOnClickListener{
            navController.navigate(R.id.action_signupFragment_to_signinFragment)
        }
        binding.nextBtn.setOnClickListener{
            var email= binding.emailEt.text.toString()
            var pass=binding.passEt.text.toString()
            var Cpass=binding.verifyPassEt.text.toString()
            if(email.isNotEmpty() && pass.isNotEmpty()&&Cpass.isNotEmpty()){

                if (Cpass==pass){
                    eventRegister(email,pass)
                    binding.progress.visibility=View.VISIBLE
                }
                else{
                    Toast.makeText(context,"Password not matching",Toast.LENGTH_SHORT).show()
                }

            }

            else{
                Toast.makeText(context,"Empty fields not allowed",Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun eventRegister(email: String, pass: String){
        auth.createUserWithEmailAndPassword(email,pass)
            .addOnCompleteListener {it->
                if (it.isSuccessful){
                    navController.navigate(R.id.action_signupFragment_to_signinFragment)
                    Toast.makeText(context,"Signup successfully",Toast.LENGTH_SHORT).show()

                }
                else{
                    Toast.makeText(context,"invalid credentials",Toast.LENGTH_SHORT).show()
                }
                binding.progress.visibility=View.GONE
            }

    }


}