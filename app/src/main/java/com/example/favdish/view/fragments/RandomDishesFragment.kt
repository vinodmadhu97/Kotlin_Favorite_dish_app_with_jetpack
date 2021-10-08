package com.example.favdish.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.favdish.databinding.FragmentRandomDishesBinding
import com.example.favdish.viewModel.NotificationsViewModel
import com.example.favdish.viewModel.RandomDishViewModel

class RandomDishesFragment : Fragment() {

    private var mBinding : FragmentRandomDishesBinding? = null
    private lateinit var mRandomDishModel: RandomDishViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = FragmentRandomDishesBinding.inflate(inflater,container,false)


        return mBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRandomDishModel = ViewModelProvider(this).get(RandomDishViewModel::class.java)
        mRandomDishModel.getRandomDishFromApi()
        randomDishViewModelObserver()
    }


    private fun randomDishViewModelObserver(){
        mRandomDishModel.randomDishResponse.observe(viewLifecycleOwner,
            {
                randomDishResponse -> randomDishResponse?.let {
                   Log.i("random_dish","${randomDishResponse.recipes[0]}")
                }
            }
            )

        mRandomDishModel.randomDishLoadingError.observe(viewLifecycleOwner,
            {
                dataError -> dataError?.let {
                   Log.e("random_dish_error","$dataError")
                }
            }
            )

        mRandomDishModel.loadRandomDish.observe(viewLifecycleOwner,{
            loadRandomDish -> loadRandomDish?.let {
                Log.e("random_dish_loading","$loadRandomDish")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}