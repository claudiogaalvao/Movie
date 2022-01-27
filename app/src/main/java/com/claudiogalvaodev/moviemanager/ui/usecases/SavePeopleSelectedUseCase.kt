package com.claudiogalvaodev.moviemanager.ui.usecases

import com.claudiogalvaodev.moviemanager.data.bd.dao.EmployeDao
import com.claudiogalvaodev.moviemanager.model.Employe

class SavePeopleSelectedUseCase(
    private val employeDao: EmployeDao
) {

    suspend operator fun invoke(people: List<Employe>) {
        employeDao.insertEmployeSelected(people)
    }

}