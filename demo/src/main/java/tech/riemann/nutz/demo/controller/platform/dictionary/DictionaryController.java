package tech.riemann.nutz.demo.controller.platform.dictionary;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.spring.boot.service.entity.Pagination;
import org.nutz.spring.boot.service.interfaces.EntityService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import tech.riemann.nutz.demo.entity.dictionary.Dictionary;
import tech.riemann.nutz.demo.entity.dictionary.Group;
import tech.riemann.nutz.demo.service.dictionary.DictionaryService;
import tech.riemann.nutz.demo.service.dictionary.GroupService;

/**
 * 码本分组 前端控制器
 *
 * @author Kerbores(kerbores@gmail.com)
 *
 * @since 2022-09-13 09:49:04
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "Dictionary", description = "数据字典")
public class DictionaryController {

    private final GroupService groupService;

    private final DictionaryService dictionaryService;

    /**
     * 分页查询码本分组
     * 
     * @param page
     *            页码
     * @param size
     *            分页大小
     * @param key
     *            关键词
     * @return 码本分组分页数据
     */
    @GetMapping("groups")
    @Operation(summary = "分页查询码本分组")
    public Pagination<Group> groups(
                                    @Parameter(description = "页码") @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                    @Parameter(description = "页面大小") @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                    @Parameter(description = "搜索关键词") @RequestParam(name = "key", required = false, defaultValue = "") String key) {
        if (Strings.isBlank(key)) {
            return groupService.searchByPage(page, size, Cnd.orderBy().desc(Group::getId));
        }
        key = String.format("%%%s%%", key);
        return groupService.searchByPage(page,
                                         size,
                                         Cnd.where(Group::getName, EntityService.LIKE, key)
                                            .or(Group::getDescription, EntityService.LIKE, key)
                                            .desc(Group::getId));
    }

    /**
     * 码本分组详情
     * 
     * @param id
     *            码本分组id
     * @return 码本分组
     */
    @GetMapping("group/{id}")
    @Operation(summary = "码本分组详情")
    public Group groupDetail(@Parameter(description = "码本分组id", required = true) @PathVariable("id") long id) {
        return groupService.fetch(id);
    }

    /**
     * 添加或者更新码本分组
     * 
     * @param group
     *            码本分组数据
     * @return 码本分组
     */
    @PutMapping("group")
    @Operation(summary = "增加/编辑码本分组")
    public Group saveOrUpdateGroup(@Validated @Parameter(description = "码本分组") @RequestBody Group group) {
        if (group.getId() != null && group.getId() > 0) {
            if (groupService.update(group) == 1) {
                return group;
            } else {
                throw Lang.makeThrow("更新码本分组失败!");
            }
        }
        return groupService.insert(group);
    }

    /**
     * 删除码本分组
     * 
     * @param id
     *            码本分组id
     * @return 是否删除成功
     */
    @DeleteMapping("group/{key}")
    @Operation(summary = "删除码本分组")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGroup(@Parameter(description = "码本分组key", required = true) @PathVariable("key") String key) {
        if (groupService.delete(key) != 1) {
            throw Lang.makeThrow("删除码本分组失败!");
        }
    }

    @GetMapping("group/{group}/dictionaries")
    @Operation(summary = "分组下的数据字典列表")
    public List<Dictionary> dictionaries(@Parameter(description = "码本分组key", required = true) @PathVariable("group") String group) {
        return dictionaryService.list(Cnd.where(Dictionary::getGroupKey, EntityService.EQ, group)
                                         .desc(Dictionary::getIndex)
                                         .desc(Dictionary::getId));
    }

    @PutMapping("group/{group}/dictionary")
    @Operation(summary = "增加/编辑码本数据")
    public Dictionary saveOrUpdateDictionary(
                                             @Parameter(description = "码本分组key", required = true) @PathVariable("group") String group,
                                             @Validated @Parameter(description = "码本数据") @RequestBody Dictionary dictionary) {
        dictionary.setGroupKey(group);
        if (dictionary.getId() != null && dictionary.getId() > 0) {
            if (dictionaryService.update(dictionary) == 1) {
                return dictionary;
            } else {
                throw Lang.makeThrow("更新码本数据失败!");
            }
        }
        return dictionaryService.insert(dictionary);
    }

    @DeleteMapping("group/{group}/dictionary/{key}")
    @Operation(summary = "删除码本数据")
    @ResponseStatus(HttpStatus.OK)
    public void deleteDictionary(@Parameter(description = "码本分组key", required = true) @PathVariable("group") String group,
                                 @Parameter(description = "码本数据key", required = true) @PathVariable("key") String key) {
        if (dictionaryService.clear(Cnd.where(Dictionary::getGroupKey, EntityService.EQ, group)
                                       .and(Dictionary::getKey, EntityService.EQ, key)) != 1) {
            throw Lang.makeThrow("删除码本数据失败!");
        }
    }

}
