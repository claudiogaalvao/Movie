package com.claudiogalvaodev.moviemanager.ui.usecases

import com.claudiogalvaodev.moviemanager.data.bd.dao.EmployeDao

class RemovePersonSelectedUseCase(
    private val employeDao: EmployeDao
) {

    suspend operator fun invoke(id: Long) {
        employeDao.removePersonSelected(id)
    }
}