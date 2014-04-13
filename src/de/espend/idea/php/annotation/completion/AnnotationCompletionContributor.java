package de.espend.idea.php.annotation.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.openapi.project.Project;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.documentation.phpdoc.lexer.PhpDocTokenTypes;
import com.jetbrains.php.lang.documentation.phpdoc.psi.PhpDocComment;
import com.jetbrains.php.lang.documentation.phpdoc.psi.tags.PhpDocTag;
import com.jetbrains.php.lang.psi.elements.*;
import com.jetbrains.php.lang.psi.resolve.types.PhpType;
import de.espend.idea.php.annotation.completion.insert.AnnotationTagInsertHandler;
import de.espend.idea.php.annotation.completion.lookupelements.PhpAnnotationPropertyLookupElement;
import de.espend.idea.php.annotation.completion.lookupelements.PhpClassAnnotationLookupElement;
import de.espend.idea.php.annotation.extension.parameter.AnnotationCompletionProviderParameter;
import de.espend.idea.php.annotation.dict.AnnotationProperty;
import de.espend.idea.php.annotation.dict.AnnotationPropertyEnum;
import de.espend.idea.php.annotation.dict.AnnotationTarget;
import de.espend.idea.php.annotation.dict.PhpAnnotation;
import de.espend.idea.php.annotation.extension.PhpAnnotationCompletionProvider;
import de.espend.idea.php.annotation.extension.parameter.AnnotationPropertyParameter;
import de.espend.idea.php.annotation.pattern.AnnotationPattern;
import de.espend.idea.php.annotation.util.AnnotationUtil;
import de.espend.idea.php.annotation.util.PhpElementsUtil;
import de.espend.idea.php.annotation.util.PluginUtil;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class AnnotationCompletionContributor extends CompletionContributor {

    public AnnotationCompletionContributor() {
        extend(CompletionType.BASIC, AnnotationPattern.getDocBlockTag(), new PhpDocBlockTagAnnotations());
        extend(CompletionType.BASIC, AnnotationPattern.getDocAttribute(), new PhpDocAttributeList());
        extend(CompletionType.BASIC, AnnotationPattern.getTextIdentifier(), new PhpDocAttributeValue());
        extend(CompletionType.BASIC, AnnotationPattern.getDefaultPropertyValue(), new PhpDocDefaultValue());
    }

    private class PhpDocDefaultValue  extends CompletionProvider<CompletionParameters> {

        @Override
        protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet result) {

            PsiElement psiElement = parameters.getOriginalPosition();
            if(psiElement == null | !PluginUtil.isEnabled(psiElement)) {
                return;
            }

            PhpDocTag phpDocTag = PsiTreeUtil.getParentOfType(parameters.getOriginalPosition(), PhpDocTag.class);
            PhpClass phpClass = AnnotationUtil.getAnnotationReference(phpDocTag);
            if(phpClass == null) {
                return;
            }

            AnnotationPropertyParameter annotationPropertyParameter = new AnnotationPropertyParameter(parameters.getOriginalPosition(), phpClass, AnnotationPropertyParameter.Type.DEFAULT);
            providerWalker(parameters, context, result, annotationPropertyParameter);

        }
    }

    private void providerWalker(CompletionParameters parameters, ProcessingContext context, CompletionResultSet result, AnnotationPropertyParameter annotationPropertyParameter) {
        AnnotationCompletionProviderParameter completionParameter = new AnnotationCompletionProviderParameter(parameters, context, result);

        for(PhpAnnotationCompletionProvider phpAnnotationExtension : AnnotationUtil.EXTENSION_POINT_COMPLETION.getExtensions()) {
            phpAnnotationExtension.getPropertyValueCompletions(annotationPropertyParameter, completionParameter);
        }
    }

    private class PhpDocAttributeValue extends CompletionProvider<CompletionParameters> {

        @Override
        protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet result) {

            PsiElement psiElement = parameters.getOriginalPosition();
            if(psiElement == null | !PluginUtil.isEnabled(psiElement)) {
                return;
            }

            PsiElement phpDocString = psiElement.getContext();
            if(!(phpDocString instanceof StringLiteralExpression)) {
                return;
            }

            PsiElement propertyName = PhpElementsUtil.getPrevSiblingOfPatternMatch(phpDocString, PlatformPatterns.psiElement(PhpDocTokenTypes.DOC_IDENTIFIER));
            if(propertyName == null) {
                return;
            }

            PhpDocTag phpDocTag = PsiTreeUtil.getParentOfType(parameters.getOriginalPosition(), PhpDocTag.class);
            PhpClass phpClass = AnnotationUtil.getAnnotationReference(phpDocTag);
            if(phpClass == null) {
                return;
            }

            AnnotationPropertyParameter annotationPropertyParameter = new AnnotationPropertyParameter(parameters.getOriginalPosition(), phpClass, propertyName.getText(), AnnotationPropertyParameter.Type.PROPERTY_VALUE);
            providerWalker(parameters, context, result, annotationPropertyParameter);

        }
    }

    private class PhpDocAttributeList extends CompletionProvider<CompletionParameters> {

        @Override
        protected void addCompletions(@NotNull CompletionParameters completionParameters, ProcessingContext processingContext, @NotNull CompletionResultSet completionResultSet) {
            PsiElement psiElement = completionParameters.getOriginalPosition();

            if(psiElement == null || !PluginUtil.isEnabled(psiElement)) {
                return;
            }

            PhpDocTag phpDocTag = PsiTreeUtil.getParentOfType(psiElement, PhpDocTag.class);
            if(phpDocTag == null) {
                return;
            }

            PhpClass phpClass = AnnotationUtil.getAnnotationReference(phpDocTag);
            if(phpClass == null) {
                return;
            }

            for(Field field: phpClass.getFields()) {
                attachLookupElement(completionResultSet, field);
            }

        }

        private void attachLookupElement(CompletionResultSet completionResultSet, Field field) {
            if(field.isConstant()) {
               return;
            }

            String propertyName = field.getName();

            // private $isNillable = false;
            PhpType type = field.getType();
            if(type.toString().equals("bool")) {
                completionResultSet.addElement(new PhpAnnotationPropertyLookupElement(new AnnotationProperty(propertyName, AnnotationPropertyEnum.BOOLEAN)));
                return;
            }

            PhpDocComment docComment = field.getDocComment();
            if(docComment != null) {
                for(PhpDocTag varDocTag: docComment.getTagElementsByName("@var")) {
                    PhpPsiElement phpPsiElement = varDocTag.getFirstPsiChild();
                    if(phpPsiElement != null) {
                        String typeText = phpPsiElement.getText().toLowerCase();
                        if(!StringUtils.isBlank(typeText)) {

                            // @var array<string>
                            if(typeText.startsWith("array")) {
                                completionResultSet.addElement(new PhpAnnotationPropertyLookupElement(new AnnotationProperty(propertyName, AnnotationPropertyEnum.ARRAY)));
                                return;
                            }

                            if(typeText.equals("integer") || typeText.equals("int")) {
                                completionResultSet.addElement(new PhpAnnotationPropertyLookupElement(new AnnotationProperty(propertyName, AnnotationPropertyEnum.INTEGER)));
                                return;
                            }

                            if(typeText.equals("boolean") || typeText.equals("bool")) {
                                completionResultSet.addElement(new PhpAnnotationPropertyLookupElement(new AnnotationProperty(propertyName, AnnotationPropertyEnum.BOOLEAN)));
                                return;
                            }

                        }
                    }
                }
            }

            // public $var = array();
            if(field.getDefaultValue() instanceof ArrayCreationExpression) {
                completionResultSet.addElement(new PhpAnnotationPropertyLookupElement(new AnnotationProperty(propertyName, AnnotationPropertyEnum.ARRAY)));
                return;
            }

            // fallback
            completionResultSet.addElement(new PhpAnnotationPropertyLookupElement(new AnnotationProperty(propertyName, AnnotationPropertyEnum.STRING)));
        }

    }

    private class PhpDocBlockTagAnnotations  extends CompletionProvider<CompletionParameters> {

        @Override
        protected void addCompletions(@NotNull CompletionParameters completionParameters, ProcessingContext processingContext, @NotNull CompletionResultSet completionResultSet) {

            PsiElement psiElement = completionParameters.getOriginalPosition();

            if(psiElement == null || !PluginUtil.isEnabled(psiElement)) {
                return;
            }

            AnnotationTarget annotationTarget = PhpElementsUtil.findAnnotationTarget(PsiTreeUtil.getParentOfType(psiElement, PhpDocComment.class));
            if(annotationTarget == null) {
                return;
            }

            Project project = completionParameters.getPosition().getProject();
            attachLookupElements(project, annotationTarget, completionResultSet);

        }

        private void attachLookupElements(Project project, AnnotationTarget foundTarget, CompletionResultSet completionResultSet) {
            for(PhpAnnotation phpClass: getPhpAnnotationTargetClasses(project, foundTarget)) {
                completionResultSet.addElement(new PhpClassAnnotationLookupElement(phpClass.getPhpClass()).withInsertHandler(AnnotationTagInsertHandler.getInstance()));
            }
        }

        private List<PhpAnnotation> getPhpAnnotationTargetClasses(Project project, AnnotationTarget foundTarget) {
            // @TODO: how handle unknown types
            return AnnotationUtil.getAnnotationsOnTarget(project,
                foundTarget,
                AnnotationTarget.ALL,
                AnnotationTarget.UNKNOWN,
                AnnotationTarget.UNDEFINED
            );
        }

    }

}
