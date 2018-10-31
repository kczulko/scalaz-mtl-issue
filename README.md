# scalaz-mtl-issue

If you move `MS: MonadState[F,Int]` within `def dummyProgram[F]` definition, like here 

```scala
def dummyProgram[F[_]](implicit ME: MonadError[F,Throwable], MS: MonadState[F,Int]): F[Unit]
```
and adjust materialization properly (just remove `stms` parameter application)
```scala
dummyProgram[StateT[EitherT[Id,Throwable,?],Int,?]]
```

You will end up with the following error (scala 2.12.7:

```bash
[error] /home/kczulko/Projects/scalaz-mtl-issue/src/main/scala/example/Hello.scala:13:20: value flatMap is not a member of type parameter F[Int]
[error]       i      <- MS.get
[error]                    ^
[error] /home/kczulko/Projects/scalaz-mtl-issue/src/main/scala/example/Hello.scala:14:23: value flatMap is not a member of type parameter F[Unit]
[error]       _      <- MS.put(i + 1)
[error]                       ^
[error] /home/kczulko/Projects/scalaz-mtl-issue/src/main/scala/example/Hello.scala:15:17: value map is not a member of type parameter F[_1]
[error]       result <- if (i < 0) ME.raiseError(new Throwable(":/")) else ME.point(())
[error]                 ^
[error] three errors found
[error] (Compile / compileIncremental) Compilation failed
[error] Total time: 0 s, completed 2018-10-31 21:51:54
```

Leaving `MS` as explicit parameter doesn't raise compile time error and program runs fine:

```bash
sbt:scalaz-mtl-issue> run
[info] Packaging /home/kczulko/Projects/scalaz-mtl-issue/target/scala-2.12/scalaz-mtl-issue_2.12-0.1.0-SNAPSHOT.jar ...
[info] Done packaging.
[info] Running example.Main 
success (3,())
```
