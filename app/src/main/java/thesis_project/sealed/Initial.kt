package thesis_project.sealed

sealed class Initial {

    object Success : Initial()
    object Error : Initial()


}
