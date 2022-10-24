package com.rsudanta.currencyconverter.data.mapper

import com.rsudanta.currencyconverter.data.remote.dto.ConvertDto
import com.rsudanta.currencyconverter.domain.model.Convert

fun ConvertDto.toConvert(): Convert {
    return Convert(info = info, query = query, result = result)
}