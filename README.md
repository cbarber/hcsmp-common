hcsmp-common
============

hcsmp common code

How to fix error “Updating Maven Project”. Unsupported IClasspathEntry kind=4?
http://stackoverflow.com/questions/10564684/how-to-fix-error-updating-maven-project-unsupported-iclasspathentry-kind-4

Please see https://bugs.eclipse.org/bugs/show_bug.cgi?id=374332#c14

The problem is caused by the fact that the STS (the Spring IDE/Eclipse) uses the m2e(clipse) plugin but that eclipse:eclipse has been probably been run on the project. When m2e encounters a "var" .classpath entry, it throws this error.

In order to fix this problem, you need to do the following:

    Make sure that the version of the m2e(clipse) plugin that you're running is at least 1.1.0, and preferably, the latest available. The update site is the following url: http://download.eclipse.org/technology/m2e/releases/ (1.3.1, at the moment).

    Disable the maven nature for the project (via the right-click menu)

    run mvn eclipse:clean (while your project is open in STS/eclipse)

    re-enable the maven nature.
    (Most of the time, this can be done by right-clicking on the project in question in the package explorer pane, and then choosing 'Configure'-> 'Convert to Maven Project')

