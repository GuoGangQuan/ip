
# Increment: A-JavaDoc
ChatGPT is used to write JavaDoc, most of its response was accurate, only some need editing, which save me lots of time </br>
Prompt used:
```ChatGPT prompt
<method or class code>
write a basic javadoc for this code
example of baisc javadoc:
/**
 * Returns lateral location of the specified position.
 * If the position is unset, NaN is returned.
 *
 * @param x X coordinate of position.
 * @param y Y coordinate of position.
 * @param zone Zone of position.
 * @return Lateral location.
 * @throws IllegalArgumentException If zone is <= 0.
 */
public double computeLocation(double x, double y, int zone)
    throws IllegalArgumentException {
    // ...
}
```
# Increment: A-CodeQuality
ChatGPT is used for code Quality improvement, most of my prompt is asking for further improvement of SLAP </br>
Prompt used:
```ChatGPT prompt
<class or method code>
does this follow SLAP?
```

# Increment: BCD-Extension
ChatGPT is used for generating idea on how to implement mass operation, however the idea generated was not very ideal </br>
but has given me a good direction on its implementation </br>
Prompt used:
```ChatGPT prompt
<project files related to delete command>
i want to delete multiple task at one go
```