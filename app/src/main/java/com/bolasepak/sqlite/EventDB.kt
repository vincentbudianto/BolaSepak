package com.bolasepak.sqlite

class EventDB {
    var id: String = ""
    var responseEvent : String = ""
    var responseTeam : String = ""

    constructor(id: String, responseEvent: String, responseTeam: String){
        this.id = id
        this.responseEvent = responseEvent
        this.responseTeam = responseTeam
    }

    constructor(responseEvent: String, responseTeam: String){
        this.responseEvent = responseEvent
        this.responseTeam = responseTeam
    }
}