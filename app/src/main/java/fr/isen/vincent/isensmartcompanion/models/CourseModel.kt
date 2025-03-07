package fr.isen.vincent.isensmartcompanion.models

import androidx.room.Entity
import androidx.room.PrimaryKey

data class CourseModel(
    val id: Int = 0,
    val title: String,
    val dayOfWeek: Int,
    val startTime: String,
    val endTime: String,
    val location: String,
    val instructor: String
)

class CourseGenerator {
    companion object {
        fun exampleCourses(): List<CourseModel> {
            return listOf(
                CourseModel(1, "Mathematics", 1, "08:30", "10:00", "Room A101", "Dr. Smith"),
                CourseModel(2, "Physics", 1, "10:30", "12:00", "Room B202", "Prof. Johnson"),
                CourseModel(3, "Computer Science", 2, "08:30", "10:00", "Lab C303", "Ms. Brown"),
                CourseModel(4, "Biology", 2, "10:30", "12:00", "Room A101", "Dr. Smith"),
                CourseModel(5, "History", 3, "14:00", "15:30", "Auditorium D404", "Mr. White"),
                CourseModel(6, "Literature", 3, "16:00", "17:30", "Room B202", "Prof. Johnson"),
                CourseModel(7, "Mathematics", 4, "08:30", "10:00", "Room A101", "Dr. Smith"),
                CourseModel(8, "Physics", 4, "10:30", "12:00", "Room B202", "Prof. Johnson"),
                CourseModel(9, "Computer Science", 5, "08:30", "10:00", "Lab C303", "Ms. Brown"),
                CourseModel(10, "Biology", 5, "10:30", "12:00", "Room A101", "Dr. Smith")
            )
        }
    }
}
