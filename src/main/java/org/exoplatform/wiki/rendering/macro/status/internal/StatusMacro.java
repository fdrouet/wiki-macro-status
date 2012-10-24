/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.exoplatform.wiki.rendering.macro.status.internal;

import java.util.Collections;
import java.util.List;


import javax.inject.Named;
import org.exoplatform.wiki.rendering.macro.status.StatusMacroParameters;
import org.xwiki.component.annotation.Component;
import org.xwiki.rendering.block.Block;
import org.xwiki.rendering.block.RawBlock;
import org.xwiki.rendering.macro.AbstractMacro;
import org.xwiki.rendering.macro.MacroExecutionException;
import org.xwiki.rendering.syntax.Syntax;
import org.xwiki.rendering.transformation.MacroTransformationContext;

/**
 * A status Macro.
 */
@Component
@Named("status")
public class StatusMacro extends AbstractMacro<StatusMacroParameters> {
    /**
     * The description of the macro.
     */
    private static final String DESCRIPTION = "Status Macro";

    private static final String[] COLORS_OK = {"#DDFADE", "#93C49F", "black"};
    private static final String[] COLORS_WARN = {"#FFD", "#F7DF92", "black"};
    private static final String[] COLORS_KO = {"#FFE7E7", "#DF9898", "black"};
    private static final String[] COLORS_UNKNOWN = {"#F0F0F0", "#BBB", "black"};

    /**
     * Create and initialize the descriptor of the macro.
     */
    public StatusMacro() {
        super("Status", DESCRIPTION, StatusMacroParameters.class);
        setDefaultCategory(DEFAULT_CATEGORY_FORMATTING);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.xwiki.rendering.macro.Macro#execute(Object, String, MacroTransformationContext)
     */
    public List<Block> execute(StatusMacroParameters parameters, String content, MacroTransformationContext context)
            throws MacroExecutionException {
//        List<Block> result;

        // List<Block> wordBlockAsList = Arrays.<Block>asList(new WordBlock(parameters.getType()));

        String type = parameters.getType();
        String[] colors;
        if (StatusMacroParameters.TYPE_OK.equalsIgnoreCase(type)) {
            colors = COLORS_OK;
        } else if (StatusMacroParameters.TYPE_WARN.equalsIgnoreCase(type)) {
            colors = COLORS_WARN;
        } else if (StatusMacroParameters.TYPE_KO.equalsIgnoreCase(type)) {
            colors = COLORS_KO;
        } else {
            colors = COLORS_UNKNOWN;
        }
        StringBuilder sb = new StringBuilder(250);
        sb.append("<span style=\"background: ");
        sb.append(colors[0]);
        sb.append(";border: 2px solid ");
        sb.append(colors[1]);
        sb.append(";color: ");
        sb.append(colors[2]);
        sb.append(";font-weight: bold;padding: 1px 4px; -moz-border-radius:6px;-webkit-border-radius: 6px; display: inline-block; text-align:center;min-width:10px;\">");
        sb.append(content);
        sb.append("</span>");
        RawBlock statusBlock = new RawBlock(sb.toString(), Syntax.XHTML_1_0);

/*
        // Handle both inline mode and standalone mode.
        if (context.isInline()) {
            result = wordBlockAsList;
        } else {
            // Wrap the result in a Paragraph Block since a WordBlock is an inline element and it needs to be
            // inside a standalone block.
            result = Arrays.<Block>asList(new ParagraphBlock(wordBlockAsList));
        }
*/

        return Collections.singletonList((Block) statusBlock);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.xwiki.rendering.macro.Macro#supportsInlineMode()
     */
    public boolean supportsInlineMode() {
        return true;
    }
}
