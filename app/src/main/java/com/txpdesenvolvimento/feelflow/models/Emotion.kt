package com.txpdesenvolvimento.feelflow.models.dto

import com.txpdesenvolvimento.feelflow.models.BaseEmotion

class Emotion(id: Int,
              userId: Int,
              description: String,
              primaryType: EmotionType,
              primaryWeight: Float,
              secondaryType: EmotionType,
              secondaryWeight: Float,

): BaseEmotion(id, userId, description) {

    private var _primaryType : EmotionType
    var primaryType : EmotionType
        get() = _primaryType
        set(value) {
            this._primaryType = value
        }

    private var _primaryWeight : Float
    var primaryWeight : Float
        get() = _primaryWeight
        set(value) {
            this._primaryWeight = value
        }

    private var _secondaryType : EmotionType
    var secondaryType : EmotionType
        get() = _secondaryType
        set(value) {
            this._secondaryType = value
        }

    private var _secondaryWeight : Float
    var secondaryWeight : Float
        get() = _secondaryWeight
        set(value) {
            this._secondaryWeight = value
        }

    init{
        this._primaryType = primaryType
        this._primaryWeight = primaryWeight
        this._secondaryType = secondaryType
        this._secondaryWeight = secondaryWeight
    }

    class EmotionType(id: Int, description: String){

        private var _id: Int
        var id : Int
            get() = _id
            set(value){
                _id = value
            }

        private var _description: String
        var description: String
            get() = _description
            set(value){
                _description = value
            }

        init {
            this._id = id
            this._description = description
        }
    }
}