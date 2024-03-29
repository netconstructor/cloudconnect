/**
 * Mule Cloud Connector Development Kit
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mule.tools.cloudconnect.generator.directives;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class SingularizeDirective implements TemplateDirectiveModel
{

    @SuppressWarnings("rawtypes")
    public void execute(Environment environment,
                        Map params,
                        TemplateModel[] templateModels,
                        TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException
    {
        if (!params.isEmpty())
        {
            throw new TemplateModelException("This directive doesn't allow any parameter");
        }
        if (templateModels.length != 0)
        {
            throw new TemplateModelException("This directive doesn't allow loop variables.");
        }

        if (templateDirectiveBody != null)
        {
            templateDirectiveBody.render(new SingularizeWriter(environment.getOut()));
        }
        else
        {
            throw new RuntimeException("Missing body");
        }
    }

    private static class SingularizeWriter extends Writer
    {

        private final Writer out;

        SingularizeWriter(Writer output)
        {
            this.out = output;
        }

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException
        {
            String word = new String(cbuf, off, len);

            out.write(Inflection.singularize(word));
        }

        @Override
        public void flush() throws IOException
        {
            out.flush();
        }

        @Override
        public void close() throws IOException
        {
            out.close();
        }
    }

    private static class Inflection
    {

        private static final List<Inflection> plural = new ArrayList<Inflection>();
        private static final List<Inflection> singular = new ArrayList<Inflection>();
        private static final List<String> uncountable = new ArrayList<String>();

        static
        {
            // plural is "singular to plural form"
            // singular is "plural to singular form"
            plural("$", "s");
            plural("s$", "s");
            plural("(ax|test)is$", "$1es");
            plural("(octop|vir)us$", "$1i");
            plural("(alias|status)$", "$1es");
            plural("(bu)s$", "$1ses");
            plural("(buffal|tomat)o$", "$1oes");
            plural("([ti])um$", "$1a");
            plural("sis$", "ses");
            plural("(?:([^f])fe|([lr])f)$", "$1$2ves");
            plural("(hive)$", "$1s");
            plural("([^aeiouy]|qu)y$", "$1ies");
            //plural("([^aeiouy]|qu)ies$", "$1y");
            plural("(x|ch|ss|sh)$", "$1es");
            plural("(matr|vert|ind)ix|ex$", "$1ices");
            plural("([m|l])ouse$", "$1ice");
            plural("^(ox)$", "$1en");
            plural("(quiz)$", "$1zes");

            singular("s$", "");
            singular("(n)ews$", "$1ews");
            singular("([ti])a$", "$1um");
            singular("((a)naly|(b)a|(d)iagno|(p)arenthe|(p)rogno|(s)ynop|(t)he)ses$", "$1$2sis");
            singular("(^analy)ses$", "$1sis");
            singular("([^f])ves$", "$1fe");
            singular("(hive)s$", "$1");
            singular("(tive)s$", "$1");
            singular("([lr])ves$", "$1f");
            singular("([^aeiouy]|qu)ies$", "$1y");
            singular("(s)eries$", "$1eries");
            singular("(m)ovies$", "$1ovie");
            singular("(x|ch|ss|sh)es$", "$1");
            singular("([m|l])ice$", "$1ouse");
            singular("(bus)es$", "$1");
            singular("(o)es$", "$1");
            singular("(shoe)s$", "$1");
            singular("(cris|ax|test)es$", "$1is");
            singular("(octop|vir)i$", "$1us");
            singular("(alias|status)es$", "$1");
            singular("^(ox)en", "$1");
            singular("(vert|ind)ices$", "$1ex");
            singular("(matr)ices$", "$1ix");
            singular("(quiz)zes$", "$1");

            // irregular
            irregular("person", "people");
            irregular("man", "men");
            irregular("child", "children");
            irregular("sex", "sexes");
            irregular("move", "moves");

            uncountable("equipment");
            uncountable("information");
            uncountable("rice");
            uncountable("money");
            uncountable("species");
            uncountable("series");
            uncountable("fish");
            uncountable("sheep");

            //Collections.reverse(singular);
            //Collections.reverse(plural);
        }

        private String pattern;
        private String replacement;
        private boolean ignoreCase;

        public Inflection(String pattern)
        {
            this(pattern, null, true);
        }

        public Inflection(String pattern, String replacement)
        {
            this(pattern, replacement, true);
        }

        public Inflection(String pattern, String replacement, boolean ignoreCase)
        {
            this.pattern = pattern;
            this.replacement = replacement;
            this.ignoreCase = ignoreCase;
        }

        private static void plural(String pattern, String replacement)
        {
            plural.add(0, new Inflection(pattern, replacement));
        }

        private static void singular(String pattern, String replacement)
        {
            singular.add(0, new Inflection(pattern, replacement));
        }

        private static void irregular(String s, String p)
        {
            plural("(" + s.substring(0, 1) + ")" + s.substring(1) + "$", "$1" + p.substring(1));
            singular("(" + p.substring(0, 1) + ")" + p.substring(1) + "$", "$1" + s.substring(1));
        }

        private static void uncountable(String word)
        {
            uncountable.add(word);
        }

        /**
         * Does the given word match?
         *
         * @param word The word
         * @return True if it matches the inflection pattern
         */
        public boolean match(String word)
        {
            int flags = 0;
            if (ignoreCase)
            {
                flags = flags | Pattern.CASE_INSENSITIVE;
            }
            return Pattern.compile(pattern, flags).matcher(word).find();
        }

        /**
         * Replace the word with its pattern.
         *
         * @param word The word
         * @return The result
         */
        public String replace(String word)
        {
            int flags = 0;
            if (ignoreCase)
            {
                flags = flags | Pattern.CASE_INSENSITIVE;
            }
            return Pattern.compile(pattern, flags).matcher(word).replaceAll(replacement);
        }

        /**
         * Return the pluralized version of a word.
         *
         * @param word The word
         * @return The pluralized word
         */
        public static String pluralize(String word)
        {
            if (Inflection.isUncountable(word))
            {
                return word;
            }
            else
            {
                for (Inflection inflection : plural)
                {
                    //                System.out.println(word + " matches " + inflection.pattern + "? (ignore case: " + inflection.ignoreCase + ")");
                    if (inflection.match(word))
                    {
                        //                    System.out.println("match!");
                        return inflection.replace(word);
                    }
                }
                return word;
            }
        }

        /**
         * Return the singularized version of a word.
         *
         * @param word The word
         * @return The singularized word
         */
        public static String singularize(String word)
        {
            if (Inflection.isUncountable(word))
            {
                return word;
            }
            else
            {
                for (Inflection inflection : singular)
                {
                    //System.out.println(word + " matches " + inflection.pattern + "? (ignore case: " + inflection.ignoreCase + ")");
                    if (inflection.match(word))
                    {
                        //System.out.println("match!");
                        return inflection.replace(word);
                    }
                }
            }
            return word;
        }

        /**
         * Return true if the word is uncountable.
         *
         * @param word The word
         * @return True if it is uncountable
         */
        public static boolean isUncountable(String word)
        {
            for (String w : uncountable)
            {
                if (w.equalsIgnoreCase(word))
                {
                    return true;
                }
            }
            return false;
        }

    }
}
