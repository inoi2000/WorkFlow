package com.petproject.workflow.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.petproject.workflow.domain.entities.Approval
import com.petproject.workflow.domain.entities.BusinessTrip
import com.petproject.workflow.domain.entities.Employee
import com.petproject.workflow.domain.entities.Status
import com.petproject.workflow.domain.entities.Task
import com.petproject.workflow.domain.entities.Vacation
import com.petproject.workflow.domain.repositories.EmployeeRepository
import java.util.UUID

class EmployeeRepositoryImpl : EmployeeRepository {

    companion object {
        private const val EMPLOYEES_REFERENCE_PATH = "Employees"
    }

    private val employeesDatabase: DatabaseReference =
        Firebase.database.getReference(EMPLOYEES_REFERENCE_PATH)

    private val employee = MutableLiveData<Employee>()

//    private val employeeList: MutableList<Employee> = mutableListOf()

//    init {
//        val businessTrip = BusinessTrip(
//            id = UUID.randomUUID().toString(),
//            start = "25.11.2024",
//            end = "25.12.2024",
//            place = "г.Саратов"
//        )
//        val vacation = Vacation(
//            id = UUID.randomUUID().toString(),
//            start = "25.12.2024",
//            end = "27.12.2024"
//        )
//        val task = Task(
//            id = UUID.randomUUID().toString(),
//            description = "Разработать главный экран",
//            creation = "20.11.2024",
//            deadline = "30.12.2024",
//            status = Status.NOT_COMPLETED
//        )
//        val approval = Approval(
//            taskId = task.id,
//            employeeId = "uAI7ztSoNDZmpGIdx3KE7jA7YWR2"
//        )
//        val employee = Employee(
//            id = "uAI7ztSoNDZmpGIdx3KE7jA7YWR2",
//            name = "Авинов Михаил",
//            gender = Employee.GENDER_MAIE,
//            phone = "89033653774",
//            email = "mishavinov@mail.ru",
//            dob = "10.05.1997",
//            registered = "01.10.2024",
//            pictureUri = EmployeePictureUri("_", "_", "_"),
//            position = "Разработчик",
//            divisionId = UUID.randomUUID().toString(),
//            businessTrips = mutableListOf<BusinessTrip>(businessTrip),
//            vacations = mutableListOf<Vacation>(vacation),
//            tasks = mutableListOf<Task>(task),
//            onApproval = mutableListOf(approval)
//        )
//        employeesDatabase.child("uAI7ztSoNDZmpGIdx3KE7jA7YWR2").setValue(employee)
//    }


    override fun getEmployee(id: String): LiveData<Employee> {
        employeesDatabase.child(id).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val temp = dataSnapshot.getValue(Employee::class.java)
                employee.value = temp!!
            }

            override fun onCancelled(databaseError: DatabaseError) { }
        })
        return employee
//        val employee = employeeList.find { it.id == id }
//        if (employee != null) {
//            return employee
//        } else {
//            throw IllegalArgumentException("The employee not found")
//        }
    }
}