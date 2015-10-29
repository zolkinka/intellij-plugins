import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.StdModuleTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.project.ex.ProjectManagerEx;
import com.intellij.openapi.projectRoots.JavaSdk;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.impl.VirtualFilePointerManagerImpl;
import com.intellij.openapi.vfs.pointers.VirtualFilePointerManager;
import com.intellij.psi.PsiFile;
import com.intellij.testFramework.LightPlatformTestCase;
import org.jetbrains.annotations.NotNull;
import training.editor.EduEditor;
import training.lesson.CourseManager;

/**
 * Created by karashevich on 16/10/15.
 */

public class OpenEduEditorTest extends LightPlatformTestCase {


    @Override
    protected Sdk getProjectJDK() {
//        return super.getProjectJDK();
        JavaSdk javaSdk = JavaSdk.getInstance();
        final String suggestedHomePath = javaSdk.suggestHomePath();
        final String versionString = javaSdk.getVersionString(suggestedHomePath);
        return javaSdk.createJdk(javaSdk.getVersion(versionString).name(), suggestedHomePath);
    }

    @NotNull
    @Override
    protected ModuleType getModuleType() {
        return StdModuleTypes.JAVA;
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
    }



    public void testOpenEduEditor() throws Exception {
        final Project project = LightPlatformTestCase.getProject();
        final PsiFile file = LightPlatformTestCase.createFile("Test.java", "");
        final VirtualFile vf = file.getVirtualFile();

        EduEditor eduEditor = null;
        try {
            eduEditor = CourseManager.getInstance().getEduEditor(project, vf);
            assertNotNull(eduEditor);
        } finally {
            Disposer.dispose(eduEditor);
        }
    }

    public void testOpenEduEditorInEduProject() throws Exception {

        final Project eduProject = CourseManager.getInstance().getEduProject();
        ((VirtualFilePointerManagerImpl)VirtualFilePointerManager.getInstance()).storePointers();

        final PsiFile file = LightPlatformTestCase.createFile("Test.java", "");
        final VirtualFile vf = file.getVirtualFile();

        EduEditor eduEditor = null;
        try {
            eduEditor = CourseManager.getInstance().getEduEditor(eduProject, vf);
            assertNotNull(eduProject);
        } finally {
            Disposer.dispose(eduEditor);
            ((ProjectManagerEx) ProjectManager.getInstance()).closeAndDispose(eduProject);
        }
    }

}