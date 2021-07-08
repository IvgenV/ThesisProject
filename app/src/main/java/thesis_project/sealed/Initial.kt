package thesis_project.sealed

import thesis_project.Constnsts

sealed class Initial{

    object Success:Initial()
    object Error:Initial()


}
