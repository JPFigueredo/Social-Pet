package vortex.project.unify.Views.ui.Activity.Fragment.Main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_splash_screen.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import vortex.project.unify.R

class SplashScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_splash_screen, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setWidgets()

        val alphaAnimation = AlphaAnimation(0.1f, 2.0f)
        alphaAnimation.duration = 4000
        alphaAnimation.fillAfter = true

        val animationSet = AnimationSet(true)
        animationSet.duration = 1000
        animationSet.fillAfter = true
        animationSet.addAnimation(alphaAnimation)

        animationSet.setAnimationListener(
            object:
                Animation
                .AnimationListener{
                override fun onAnimationStart(
                    animation: Animation?) {
                    Log.d("AnimationSplash", "Start")
                }

                override fun onAnimationEnd(
                    animation: Animation?) {
                    Log.d("AnimationSplash", "End")
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(2000)
                        findNavController().navigate(
                            R.id.action_splashScreenFragment_to_loginFragment)
                    }
                }

                override fun onAnimationRepeat(
                    animation: Animation?) {
                    Log.d("AnimationSplash", "Repeat")
                }

            })

        img_logo_plash.startAnimation(animationSet)
    }

    private fun setWidgets() {
        activity?.bottom_nav_view!!.visibility = View.GONE
        activity?.toolbar_layout!!.visibility = View.GONE
    }
}