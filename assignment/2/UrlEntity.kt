package com.example.appcentertask2

class UrlEntity { //JSON 구조  => 전체 -> result -> url
    var result: Result? = null //result에는 "result"배열에대응된다. (이름이 같아서? 구조가 같아서?)

    inner class Result {
        var url: String? = null //"result" 배열 안에 있는 "url"의 값에 대응된다. ("url"은 key)
    }

}

