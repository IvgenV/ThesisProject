package thesis_project.sealed

sealed class SealedInOut{

    object In:SealedInOut()
    object Out:SealedInOut()
    object Error:SealedInOut()

}
