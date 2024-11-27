package com.example.mercoapp.repository

import com.example.mercoapp.domain.model.Order
import com.example.mercoapp.domain.model.OrderStatus
import com.example.mercoapp.service.OrderService
import com.example.mercoapp.service.OrderServiceImpl


interface OrderRepository {
    suspend fun createOrder(order: Order): String
    suspend fun getOrderById(orderId: String): Order?
    suspend fun getOrdersByBuyer(buyerId: String): List<Order>
    suspend fun updateOrderStatus(orderId: String, status: OrderStatus)
    suspend fun deleteOrder(orderId: String)
}

class OrderRepositoryImpl(
    private val orderService: OrderService = OrderServiceImpl()
) : OrderRepository {
    override suspend fun createOrder(order: Order): String {
        return orderService.createOrder(order)
    }

    override suspend fun getOrderById(orderId: String): Order? {
        return orderService.getOrderById(orderId)
    }

    override suspend fun getOrdersByBuyer(buyerId: String): List<Order> {
        return orderService.getOrdersByBuyer(buyerId)
    }

    override suspend fun updateOrderStatus(orderId: String, status: OrderStatus) {
        orderService.updateOrderStatus(orderId, status)
    }

    override suspend fun deleteOrder(orderId: String) {
        orderService.deleteOrder(orderId)
    }
}
