package ru.nsu.util

import kotlin.math.pow

fun separate(maxLen : Int, workerCount : Int) : ArrayList<Int>{
    val array = ArrayList<Int>()
    var operationCount = 0.0
    for (i in 1..maxLen) {
        operationCount += 62.0.pow(i.toDouble())
    }
    for(i in 1..workerCount){
        if(i == workerCount - 1){
            array.add(operationCount.toInt() / workerCount + operationCount.toInt() % workerCount)
        } else {
            array.add(operationCount.toInt() / workerCount)
        }
    }
    return array
}