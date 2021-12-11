package com.udacity.environmentvariables

object ProjectData {
    private var url: String? = null
    private var title: String? = null
    private var projectDesc: String? = null

    fun changeProjectDetails(title:String, projectDesc:String, url:String){
        this.url = url
        this.title = title
        this.projectDesc = projectDesc
    }

    fun getUrl (): String? = url.toString()
    fun getTitle():String? = title
    fun getDesc(): String? = projectDesc
}