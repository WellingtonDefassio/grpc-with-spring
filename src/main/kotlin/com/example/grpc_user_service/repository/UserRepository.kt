package com.example.grpc_user_service.repository

import com.example.grpc_user_service.entity.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Int>