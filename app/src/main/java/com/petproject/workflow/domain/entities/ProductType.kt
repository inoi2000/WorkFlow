package com.petproject.workflow.domain.entities

enum class ProductType {
    PURCHASED_FOR_PRODUCTION, // Продукты, закупленные для производства и потребления
    OWN_PRODUCTION, // Продукты произведенные на предприятии
    CUSTOMER_WASTE, // Отходы полученные от заказчиков
    OWN_WASTE, // Собственные отходы
    CUSTOMER_OWNED // Продукты, принадлежащие заказчику
}