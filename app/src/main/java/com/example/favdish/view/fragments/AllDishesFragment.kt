package com.example.favdish.view.fragments


import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log

import android.view.*

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.favdish.R
import com.example.favdish.application.FavDishApplication
import com.example.favdish.databinding.DialogCustomListBinding
import com.example.favdish.databinding.FragmentAllDishesBinding
import com.example.favdish.model.entities.FavDish
import com.example.favdish.utils.Constants
import com.example.favdish.view.activities.AddUpdateDishActivity


import com.example.favdish.view.activities.MainActivity
import com.example.favdish.view.adapters.CustomListItemAdapter
import com.example.favdish.view.adapters.FavDishAdapter
import com.example.favdish.viewModel.FavDishViewModel
import com.example.favdish.viewModel.FavDishViewModelFactory



class AllDishesFragment : Fragment() {

    private lateinit var mBinding : FragmentAllDishesBinding
    private var _binding: FragmentAllDishesBinding? = null

    private lateinit var mAdapter: FavDishAdapter
    private lateinit var mCustomListDialog : Dialog

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    private val mFavDishViewModel : FavDishViewModel by viewModels{
        FavDishViewModelFactory((requireActivity().application as FavDishApplication).repository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentAllDishesBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.rvDishesList.layoutManager = GridLayoutManager(requireActivity(),2)
        mAdapter = FavDishAdapter(this@AllDishesFragment)

        mBinding.rvDishesList.adapter = mAdapter

        mFavDishViewModel.allDishesList.observe(viewLifecycleOwner){
            dishes ->

                dishes.let {
                    if (it.isNotEmpty()){
                        mBinding.rvDishesList.visibility = View.VISIBLE
                        mBinding.tvNoDishesAddedYet.visibility = View.GONE
                        mAdapter.dishesList(it)
                    }else{
                        mBinding.rvDishesList.visibility = View.GONE
                        mBinding.tvNoDishesAddedYet.visibility = View.VISIBLE
                    }
                }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add_all_dishes,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_add_dishes -> {
                startActivity(Intent(requireActivity(), AddUpdateDishActivity::class.java))
                return true
            }

            R.id.action_filter_dishes ->{
                filterDishesListDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun dishDetails(favDish:FavDish){
        findNavController().navigate(AllDishesFragmentDirections.actionNavigationAllDishesToNavigationDishDetails(favDish))
        //hide navigation bar
        if (requireActivity() is MainActivity){
            (activity as MainActivity?)?.hideBottomNavigationView()
        }
    }

    fun deleteDish(favDish: FavDish){
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(resources.getString(R.string.msg_delete_dish_dialog_title))
        builder.setMessage(resources.getString(R.string.msg_delete_dish_dialog_message,favDish.title))
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setPositiveButton("Yes"){ dialogInterface,_ ->
            mFavDishViewModel.delete(favDish)
            dialogInterface.dismiss()
        }

        builder.setNegativeButton("No"){
            dialogInterface,_ -> dialogInterface.dismiss()
        }

        val alertDialog : AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()

    }

    private fun filterDishesListDialog(){
        mCustomListDialog = Dialog(requireActivity())
        val binding = DialogCustomListBinding.inflate(layoutInflater)

        mCustomListDialog.setContentView(binding.root)
        binding.tvTitle.setText("Select a Dish")

        val dishTypes = Constants.dishTypes()
        dishTypes.add(0,Constants.ALL_DISHES)

        binding.rvList.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = CustomListItemAdapter(requireActivity(),this,dishTypes,Constants.DISH_TYPE)

        binding.rvList.adapter = adapter
        mCustomListDialog.show()


    }

    override fun onResume() {
        super.onResume()

        //show Navigation bar
        if (requireActivity() is MainActivity){
            (activity as MainActivity?)?.showBottomNavigationView()
        }
    }

    fun filterSelection(filterItemSelection : String){
        mCustomListDialog.dismiss()
        Log.i("filter",filterItemSelection)
         if (filterItemSelection == Constants.ALL_DISHES){
            mFavDishViewModel.allDishesList.observe(viewLifecycleOwner){
                dishes -> dishes.let {
                    if (it.isNotEmpty()){
                        mBinding.rvDishesList.visibility = View.VISIBLE
                        mBinding.tvNoDishesAddedYet.visibility = View.GONE
                        mAdapter.dishesList(it)
                    }else{
                        mBinding.rvDishesList.visibility = View.GONE
                        mBinding.tvNoDishesAddedYet.visibility = View.VISIBLE
                    }
                }
            }
         }else{
             mFavDishViewModel.getFilteredList(filterItemSelection).observe(viewLifecycleOwner){
                 dishes -> dishes.let {
                     if (it.isNotEmpty()){
                         mBinding.rvDishesList.visibility = View.VISIBLE
                         mBinding.tvNoDishesAddedYet.visibility = View.GONE
                         mAdapter.dishesList(it)
                     }else{
                         mBinding.rvDishesList.visibility = View.GONE
                         mBinding.tvNoDishesAddedYet.visibility = View.VISIBLE
                     }
             }
             }
         }
    }


}