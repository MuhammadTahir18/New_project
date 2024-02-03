package com.firstproject.new_project.fragements

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.firstproject.new_project.R
import com.firstproject.new_project.databinding.FragmentSplashBinding
import com.google.firebase.auth.FirebaseAuth

class splashFragment : Fragment() {
private lateinit var binding:FragmentSplashBinding
  private lateinit var auth: FirebaseAuth
  private lateinit var navController: NavController
  private lateinit var topanim:Animation
    private lateinit var bottompanim:Animation
    private lateinit var textView: TextView
    private lateinit var imageView: ImageView




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        registerEvent()

        topanim=AnimationUtils.loadAnimation(context,R.anim.top_animation)
        bottompanim=AnimationUtils.loadAnimation(context,R.anim.bottom_animation)
        imageView=view.findViewById(R.id.imageView)
        textView=view.findViewById(R.id.textView)

      imageView.setAnimation(topanim)
        textView.setAnimation(bottompanim)
    }
    private fun init(view: View){
        auth=FirebaseAuth.getInstance()
        navController=Navigation.findNavController(view)
    }
    private fun registerEvent(){
        val islogin=auth.currentUser!=null
        val handler=Handler(Looper.myLooper()!!)
        handler.postDelayed({
            if (islogin) {
                navController.navigate(R.id.action_splashFragment_to_homeFragment)
            }
            else{
                navController.navigate(R.id.action_splashFragment_to_signinFragment)
            }
        },2000)




    }


}