/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package software.amazon.awssdk.archtests;

import java.util.regex.Pattern;

//@description This is a description for the ArchUtils class
public final class ArchUtils {

    private ArchUtils() {
    }

    // @satisfies RRD-SREQ-3
    public static Pattern classNameToPattern(Class<?> clazz) {
        return Pattern.compile(".*/" + clazz.getCanonicalName().replace('.', '/') + ".class");
    }

    // @verifies RRD-SREQ-5, RRD-SREQ-6
    public static Pattern classNameToPattern(String className) {
        return Pattern.compile(".*/" + className.replace('.', '/') + ".class");
    }

    //@description This method should reside in the same root package
    public static boolean resideInSameRootPackage(String pkg1, String pkg2) {
        if (pkg1.startsWith(pkg2) || pkg2.startsWith(pkg1)) {
            return true;
        }
        String root1 = findRootPackage(pkg1);
        String root2 = findRootPackage(pkg2);
        return root1.equals(root2);
    }

    private static String findRootPackage(String pkg) {
        String servicePackagePrefix = "software.amazon.awssdk.services.";
        if (pkg.startsWith(servicePackagePrefix)) {
            return findRootPackage(pkg, servicePackagePrefix);
        }

        String corePackagePrefix = "software.amazon.awssdk.";
        return findRootPackage(pkg, corePackagePrefix);
    }

    private static String findRootPackage(String pkg, String packagePrefix) {
        int mayBeModuleLength = pkg.substring(packagePrefix.length(), pkg.length()).indexOf(".");
        return mayBeModuleLength == -1 ? pkg : pkg.substring(0, packagePrefix.length() + mayBeModuleLength);
    }
}
