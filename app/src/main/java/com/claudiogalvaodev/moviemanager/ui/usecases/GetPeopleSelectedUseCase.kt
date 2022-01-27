package com.claudiogalvaodev.moviemanager.ui.usecases

import com.claudiogalvaodev.moviemanager.data.bd.dao.EmployeDao
import com.claudiogalvaodev.moviemanager.model.Employe
import kotlinx.coroutines.flow.Flow

class GetPeopleSelectedUseCase(
    private val employeDao: EmployeDao
) {

    fun invoke(): Flow<List<Employe>> = employeDao.getEmployeSelected()

}