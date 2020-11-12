package com.example.asynccoroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

abstract class AsyncCoroutines<Params,Result> {
    var result : Result? = null
    open fun onPreExecute(){}
    open fun onPostExecute(result: Result?){}
    abstract fun doInBackground(vararg params: Params) : Result
    fun execute(vararg input : Params){
        GlobalScope.launch(Dispatchers.Main){
            onPreExecute()
            callAsync(*input)
        }
    }
    private suspend fun callAsync(vararg input : Params){
        GlobalScope.async(Dispatchers.IO){
            result = doInBackground(*input)
        }.await()
        GlobalScope.launch(Dispatchers.Main){
            onPostExecute(result)
        }
    }
}